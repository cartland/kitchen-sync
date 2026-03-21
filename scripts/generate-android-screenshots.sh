#!/usr/bin/env bash
set -euo pipefail

# Generate (or update) reference screenshots for android-screenshot-tests.
# Run this after changing any @PreviewTest composable.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

cd "$PROJECT_DIR"
./gradlew :android-screenshot-tests:updateDebugScreenshotTest "$@"

echo ""
echo "Reference images updated. Check:"
echo "  android-screenshot-tests/src/screenshotTestDebug/reference/"
