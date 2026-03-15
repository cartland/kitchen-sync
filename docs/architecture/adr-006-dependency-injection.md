# ADR-006: Dependency Injection — kotlin-inject

## Status

Accepted (2026-03-15)

## Context

Clean Architecture with multiple layers requires dependency injection to wire components together. The DI framework must:

- Work in Kotlin Multiplatform (no JVM-only reflection)
- Provide compile-time safety (catch wiring errors at build time, not runtime)
- Be type-safe and idiomatic Kotlin

## Decision

Use **[kotlin-inject](https://github.com/evant/kotlin-inject)** for dependency injection.

kotlin-inject is a compile-time DI framework built for Kotlin. It uses KSP (Kotlin Symbol Processing) to generate DI code at compile time, with no runtime reflection.

## Consequences

- **Positive:** Compile-time verification. Missing bindings or circular dependencies are caught during compilation, not at app startup.
- **Positive:** KMP-native. Works across all Kotlin targets without platform-specific workarounds.
- **Positive:** No reflection. Faster startup, smaller binary, and no runtime surprises.
- **Positive:** Type-safe. The Kotlin type system enforces correct wiring.
- **Negative:** Smaller community and ecosystem compared to Dagger/Hilt. Fewer tutorials, examples, and Stack Overflow answers.
- **Negative:** API is less familiar to Android developers who know Dagger/Hilt. Learning curve for the team.
- **Negative:** Code generation via KSP can increase build times, especially in multi-module projects.

## Alternatives Considered

- **Dagger/Hilt:** Industry standard for Android DI. Mature and well-documented, but JVM-only (uses `javax.inject` and annotation processing). Not compatible with KMP shared code.
- **Koin:** Popular Kotlin DI framework with KMP support. However, Koin is a service locator at runtime — no compile-time verification. Wiring errors surface as runtime crashes, which is unacceptable for a type-safe architecture.
- **Manual DI:** Pass dependencies through constructors without a framework. Viable for small projects but becomes unwieldy as the dependency graph grows. Loses the ability to scope instances (singleton, per-screen, etc.) without reinventing a framework.
