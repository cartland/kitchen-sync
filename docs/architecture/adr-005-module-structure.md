# ADR-005: Module Structure — Clean Architecture Layers

## Status

Accepted (2026-03-15)

## Context

The codebase needs a clear structure that separates concerns, makes testing straightforward, and scales as features are added. With KMP sharing business logic across platforms, the module boundaries must be well-defined so platform-specific code doesn't leak into shared logic.

## Decision

Adopt **Clean Architecture** with the following layers (inner to outer):

```
domain  →  usecase  →  data  →  presentation
```

- **`domain`**: Pure Kotlin models and interfaces. No platform dependencies, no framework imports. Defines repository interfaces, entities, and value objects.
- **`usecase`**: Business logic orchestration. Each use case is a single-purpose class that coordinates domain operations. Depends only on `domain`.
- **`data`**: Repository implementations, database (Room) DAOs, network (Firestore) data sources, mappers between data models and domain models. Implements interfaces defined in `domain`.
- **`presentation`**: UI layer. Jetpack Compose (Android) and SwiftUI (iOS). ViewModels live here, consuming use cases.

The dependency rule is strict: inner layers never depend on outer layers. Dependencies point inward.

This structure mirrors the approach used in [battery-butler](https://github.com/nicemak/battery-butler), adapted for KMP.

## Consequences

- **Positive:** Clear separation of concerns. Each layer has a single responsibility and well-defined dependencies.
- **Positive:** The `domain` and `usecase` layers are pure Kotlin, making them easy to unit test without platform dependencies.
- **Positive:** Swapping implementations (e.g., replacing Firestore with a different backend) only affects the `data` layer.
- **Positive:** Familiar pattern for Android developers. Reduces onboarding friction.
- **Negative:** More boilerplate than a simpler flat structure. Interfaces, mappers, and layer boundaries add code.
- **Negative:** Over-engineering risk for simple features that don't need multiple layers. Discipline is needed to keep it pragmatic.

## Alternatives Considered

- **Feature-based modules:** Organize by feature (`:recipes`, `:mealplan`, `:grocery`) rather than by layer. Can work well but harder to enforce the dependency rule and tends to lead to circular dependencies between features.
- **Flat structure:** All code in one module with package-level separation. Simpler for small projects but doesn't scale and makes it easy to violate layer boundaries.
- **MVI/Redux pattern:** Unidirectional data flow at the architecture level. Complementary to Clean Architecture (can be used in the presentation layer) but not a replacement for the full stack structure.
