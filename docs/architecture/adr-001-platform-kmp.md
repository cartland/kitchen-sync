# ADR-001: Platform — Kotlin Multiplatform

## Status

Accepted (2026-03-15)

## Context

Kitchen Sync needs to run on both Android and iOS from day one. The household meal-planning domain means users within the same household may be on different platforms, so shipping only one platform would immediately break the shared-household model.

We need a strategy that lets a small team ship and maintain both platforms without duplicating business logic.

## Decision

Use **Kotlin Multiplatform (KMP)** with shared business logic and native UI per platform:

- **Shared (KMP):** Domain models, use cases, data/repository layer, sync logic
- **Android UI:** Jetpack Compose
- **iOS UI:** SwiftUI

The shared module targets `commonMain` with platform-specific implementations via `expect`/`actual` declarations where needed.

## Consequences

- **Positive:** Business logic is written once and tested once. Bug fixes propagate to both platforms automatically.
- **Positive:** Native UI frameworks mean each platform feels native with full access to platform APIs.
- **Positive:** Kotlin is already the primary Android language, so Android development is idiomatic.
- **Negative:** iOS developers must work with Kotlin in the shared module, which may have a learning curve.
- **Negative:** KMP tooling is maturing but not as polished as single-platform tooling. Build configuration can be complex.
- **Negative:** Some libraries may not have KMP support, requiring platform-specific alternatives.

## Alternatives Considered

- **Flutter:** Single codebase including UI, but Dart is a separate language from the Android ecosystem, and Flutter's look-and-feel can diverge from native platform conventions.
- **React Native:** JavaScript-based cross-platform. Similar trade-offs to Flutter with additional bridge overhead and less type safety.
- **Separate native apps:** Maximum platform fidelity but doubles the business logic code and maintenance burden. Not viable for a small team.
- **Android only:** Simplest path but excludes iOS users from shared households, which contradicts the core product model.
