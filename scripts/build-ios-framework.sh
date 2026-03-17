#!/bin/bash
set -ex
cd "$(dirname "$0")/.."

# Links the Debug Framework for iOS Simulator (Apple Silicon)
echo "Linking iOS Debug Framework (Simulator Arm64)..."
./gradlew :compose-app:linkDebugFrameworkIosSimulatorArm64
