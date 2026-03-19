# Screenshot Infrastructure Migration Plan

## Problem

Kitchen-sync needs AGP Compose Screenshot Testing (`com.android.compose.screenshot`).
The screenshot plugin requires `android.experimental.enableScreenshotTest=true` in
`gradle.properties`, which is a **project-global** flag. When this flag is set, it
triggers a Kotlin 2.3.0 deprecation-warning code path in compose-app (which uses
`kotlinMultiplatform` + `androidApplication`) that crashes with:

```
IncompatibleClassChangeError: Method 'ProblemGroup.create(...)' must be InterfaceMethodref constant
```

**Root cause:** Kitchen-sync's `buildSrc` doesn't have AGP or Kotlin on its classpath.
Battery-butler's `buildSrc` does, and that resolves the classloader conflict.

## Strategy

Adopt battery-butler's build infrastructure pattern (which is proven to work with the
screenshot plugin), then layer kitchen-sync's code and docs on top. This is safer than
trying to retrofit the screenshot plugin into the current setup.

**Reference:** Battery-butler local copy at `/tmp/battery-butler/`

---

## Phase 1: Align buildSrc with battery-butler

These changes fix the classloader issue that prevents the screenshot plugin from working.

- [ ] **1.1** Add AGP + Kotlin to `buildSrc/build.gradle.kts` dependencies
  - Add `implementation("com.android.tools.build:gradle:8.9.1")`
  - Add `implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0")`
  - Add `google()` and `gradlePluginPortal()` to repositories
  - Add `java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }`
  - Keep kitchen-sync's plugin IDs (`kitchen-sync.*` prefix, `register()`)

- [ ] **1.2** Remove version refs from plugins now on buildSrc classpath (`gradle/libs.versions.toml`)
  - `androidApplication` → remove `version.ref = "agp"` (AGP on classpath)
  - `androidLibrary` → remove `version.ref = "agp"`
  - `androidKotlinMultiplatformLibrary` → remove `version.ref = "agp"`
  - `kotlinMultiplatform` → remove `version.ref = "kotlin"`
  - `kotlinJvm` → remove `version.ref = "kotlin"`
  - `kotlinSerialization` → remove `version.ref = "kotlin"`
  - Keep versions for: `composeMultiplatform`, `composeCompiler`, `spotless`, `room`, `ksp`, `detekt`

- [ ] **1.3** Remove `apply false` entries from root `build.gradle.kts` for classpath plugins
  - Remove: `androidApplication`, `androidLibrary`, `androidKotlinMultiplatformLibrary`,
    `kotlinMultiplatform`, `ksp`, `room`
  - Keep: `composeMultiplatform`, `composeCompiler`, `spotless`, `detekt`

- [ ] **1.4** Add `android.experimental.enableScreenshotTest=true` to `gradle.properties`

- [ ] **1.5** Add `kotlin.mpp.androidSourceSetLayoutV2AndroidStyleDirs.nowarn=true` to
  `gradle.properties` (suppresses noisy KMP warning)

### Phase 1 Verification

- [ ] `./gradlew checkArchitecture` passes
- [ ] `./gradlew :compose-app:assembleDebug` compiles
- [ ] `./gradlew spotlessCheck` passes
- [ ] All existing modules still resolve correctly

---

## Phase 2: Add screenshot test infrastructure

- [ ] **2.1** Add to `gradle/libs.versions.toml`:
  - Version: `screenshot = "0.0.1-alpha12"`
  - Library: `screenshot-validation-api` (`com.android.tools.screenshot:screenshot-validation-api`)
  - Library: `compose-ui-test-junit4` (`androidx.compose.ui:ui-test-junit4`)
  - Library: `compose-ui-test-manifest` (`androidx.compose.ui:ui-test-manifest`)
  - Plugin: `screenshot` (`com.android.compose.screenshot`, version.ref = "screenshot")
  - Plugin: `kotlinAndroid` (`org.jetbrains.kotlin.android`, no version — on classpath)

- [ ] **2.2** Add `include(":android-screenshot-tests")` to `settings.gradle.kts`

- [ ] **2.3** Add `android-screenshot-tests` to `ArchitectureCheckTask.kt` whitelist
  - Allowed deps: `domain`, `presentation-core`, `presentation-feature`

- [ ] **2.4** Create `android-screenshot-tests/build.gradle.kts`
  - Plugins: `androidLibrary`, `kotlinAndroid`, `composeMultiplatform`, `composeCompiler`, `screenshot`
  - `experimentalProperties["android.experimental.enableScreenshotTest"] = true`
  - Dependencies: compose.ui, compose.material3, compose.components.uiToolingPreview
  - screenshotTestImplementation: screenshot-validation-api, compose-ui-test-junit4,
    compose-ui-test-manifest
  - No dependency on `:compose-app` (AGP limitation: can't depend on application modules)

- [ ] **2.5** Create `android-screenshot-tests/src/main/AndroidManifest.xml`
  - Minimal `<manifest />`

- [ ] **2.6** Create `android-screenshot-tests/src/screenshotTest/kotlin/.../AppScreenshotTest.kt`
  - Package: `com.chriscartland.kitchensync.screenshots`
  - Standalone composable reproducing current `App()` content (MaterialTheme + Surface +
    "Kitchen Sync" text)
  - `@PreviewTest` + `@Preview` annotations for light and dark mode
  - Does NOT import from `:compose-app` (standalone composable for now)

- [ ] **2.7** Create `scripts/generate-android-screenshots.sh`
  - Runs `./gradlew :android-screenshot-tests:updateDebugScreenshotTest`
  - Make executable

### Phase 2 Verification

- [ ] `./gradlew :android-screenshot-tests:assembleDebug` compiles
- [ ] `./gradlew checkArchitecture` passes with new module
- [ ] `./gradlew spotlessCheck` passes

---

## Phase 3: Generate reference screenshots

- [ ] **3.1** Run `./gradlew :android-screenshot-tests:updateDebugScreenshotTest`
  - Generates PNGs in `android-screenshot-tests/src/screenshotTestDebug/reference/`

- [ ] **3.2** Verify PNG files exist in the reference directory

- [ ] **3.3** Commit reference images

### Phase 3 Verification

- [ ] Reference PNGs exist in `android-screenshot-tests/src/screenshotTestDebug/reference/`
- [ ] Full build still passes: `./gradlew :android-screenshot-tests:assembleDebug`

---

## What we skip (intentional)

- **CI validation step** — generation only, no `validateDebugScreenshotTest` in CI
- **ScreenshotTestTheme wrapper** — not needed until composables require CompositionLocal injection
- **PreviewCoveragePlugin** — premature with 1 test
- **OOM guard / sequential script** — unnecessary with 1 test
- **Depending on compose-app** — AGP limitation; standalone composable for now
- **Battery-butler-specific features** — Jib workarounds, server modules, experimental modules,
  compose-resources, data-network, ai module, etc.

---

## Key differences from battery-butler to preserve

Kitchen-sync intentionally differs from battery-butler in these ways (do NOT change):

| Aspect | Kitchen-sync | Battery-butler | Reason |
|--------|-------------|----------------|--------|
| Plugin ID prefix | `kitchen-sync.*` | `architecture.check` etc. | Namespace convention |
| Plugin registration | `register()` | `create()` | Equivalent, keep current style |
| Module count | 9 | 22 | Kitchen-sync is MVP scope |
| ArchitectureCheckTask | Simple name-based | Complex wildcard-based | Simpler codebase |
| Root namespace | `com.chriscartland.kitchensync` | `com.chriscartland.batterybutler` | Different app |

---

## Risk assessment

- **Phase 1 is the risky phase.** Changing how plugins resolve (buildSrc classpath vs
  version catalog) affects every module. If something breaks, revert the entire phase.
- **Phase 2 is low risk.** Adding a new module with no reverse dependencies.
- **Phase 3 is no risk.** Just running a Gradle task and committing output.

## Rollback

If Phase 1 causes unforeseen issues:
1. Revert `buildSrc/build.gradle.kts` to remove AGP/Kotlin deps
2. Restore version refs in `gradle/libs.versions.toml`
3. Restore `apply false` entries in root `build.gradle.kts`
4. Remove `gradle.properties` additions
5. Verify: `./gradlew checkArchitecture` passes
