#!/bin/bash
set -ex
cd "$(dirname "$0")/.."

# CI Parity Validation Script
# This script mimics the steps defined in .github/workflows/ci.yml
# Use this to verify that your changes will likely pass CI.

echo "--- 1. Clean Project ---"
echo "--- 1. Clean Project - SKIPPED (Relying on Incremental Build) ---"
# ./gradlew clean

echo "--- 2. Formatting (Spotless) ---"
./gradlew spotlessCheck --stacktrace

echo "--- 3. Lint ---"
./gradlew lint --stacktrace

echo "--- 3b. Detekt ---"
./gradlew detekt --stacktrace

echo "--- 3c. Custom Checks ---"
./gradlew checkArchitecture checkThemeLayer checkTestCoverage checkNamingConventions checkImportBoundary

echo "--- 4. Tests ---"
./gradlew test

echo "--- 5. Build Android App ---"
./gradlew :compose-app:assembleDebug

echo "--- 6. Build Desktop App ---"
./gradlew :compose-app:packageDistributionForCurrentOS

# iOS checks - Only run on macOS
if [[ "$OSTYPE" == "darwin"* ]]; then
    echo "--- 7. iOS Checks (macOS detected) ---"
    ./gradlew --stop  # Reclaim heap before memory-intensive iOS builds

    # Compile iOS Kotlin modules (catches import/dependency errors early)
    echo "Compiling iOS Kotlin modules..."
    ./gradlew :compose-app:compileKotlinIosSimulatorArm64

    # Check for Xcode and xcodebuild
    if command -v xcodebuild >/dev/null 2>&1; then
        echo "Building iOS App..."
        xcodebuild -project iosApp/iosApp.xcodeproj \
            -configuration Debug \
            -scheme iosApp \
            -destination 'generic/platform=iOS Simulator' \
            clean build \
            CODE_SIGN_IDENTITY="" \
            CODE_SIGNING_REQUIRED=NO \
            CODE_SIGNING_ALLOWED=NO \
            CONFIGURATION_BUILD_DIR=build/ios/
    else
        echo "Warning: xcodebuild not found. Skipping iOS xcodebuild checks."
        echo "Note: Kotlin iOS modules were still compiled above."
    fi
else
    echo "Skipping iOS checks (not on macOS)."
fi

echo "--- Validation Complete! ---"
git rev-parse HEAD > .claude/.validation-passed
