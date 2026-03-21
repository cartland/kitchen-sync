plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.9.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0")
}

gradlePlugin {
    plugins {
        register("architecture-check") {
            id = "kitchen-sync.architecture-check"
            implementationClass = "architecture.ArchitecturePlugin"
        }
        register("theme-layer-check") {
            id = "kitchen-sync.theme-layer-check"
            implementationClass = "themelayer.ThemeLayerPlugin"
        }
        register("test-coverage-check") {
            id = "kitchen-sync.test-coverage-check"
            implementationClass = "testcoverage.TestCoveragePlugin"
        }
        register("naming-convention-check") {
            id = "kitchen-sync.naming-convention-check"
            implementationClass = "namingconvention.NamingConventionPlugin"
        }
        register("import-boundary-check") {
            id = "kitchen-sync.import-boundary-check"
            implementationClass = "importboundary.ImportBoundaryPlugin"
        }
        register("datastore-singleton-check") {
            id = "kitchen-sync.datastore-singleton-check"
            implementationClass = "datastoreguard.DataStoreSingletonPlugin"
        }
        register("preview-coverage-check") {
            id = "kitchen-sync.preview-coverage-check"
            implementationClass = "screenshotcoverage.PreviewCoveragePlugin"
        }
    }
}
