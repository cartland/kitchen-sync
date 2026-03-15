# ADR-007: Error Handling — Result\<D, E\> Sealed Type

## Status

Accepted (2026-03-15)

## Context

Error handling strategy affects every layer of the application. In Kotlin, the default approach of throwing exceptions has several problems:

- Exceptions are invisible in function signatures (no checked exceptions in Kotlin)
- Callers can forget to handle errors, leading to crashes
- Exception-based control flow is hard to reason about and test

For a KMP app that must work reliably offline with sync operations that can fail in many ways, error handling must be explicit and exhaustive.

## Decision

Use a **`Result<D, E>` sealed type** for all fallible operations:

```kotlin
sealed interface Result<out D, out E> {
    data class Success<D>(val data: D) : Result<D, Nothing>
    data class Failure<E>(val error: E) : Result<Nothing, E>
}
```

Rules:
1. **Never throw exceptions** from application code, with the sole exception of `CancellationException` (required by Kotlin coroutines for structured concurrency).
2. All fallible functions return `Result<D, E>` where `D` is the success type and `E` is a domain-specific error type (typically a sealed class/interface).
3. Callers use **exhaustive `when` expressions** to handle both success and failure cases. The compiler enforces that no case is missed.
4. Boundary code (e.g., calling Firebase SDK, Room, or platform APIs) wraps external exceptions into `Result.Failure` at the call site.

## Consequences

- **Positive:** Errors are visible in function signatures. Every caller knows a function can fail and must handle it.
- **Positive:** Exhaustive `when` ensures all error cases are handled. Adding a new error variant produces compiler errors at every unhandled call site.
- **Positive:** No hidden control flow. Functions return normally; errors are values, not exceptions.
- **Positive:** Easy to test. Assert on `Result.Success` or `Result.Failure` without try/catch.
- **Negative:** More verbose than throwing exceptions. Every fallible call requires unwrapping the result.
- **Negative:** Chaining multiple fallible operations requires helper functions (e.g., `map`, `flatMap`, `fold`) to avoid deep nesting.
- **Negative:** Team must consistently follow the "no throw" rule. A single thrown exception can bypass the entire error handling strategy.

## Alternatives Considered

- **Kotlin's built-in `Result<T>`:** Only has one generic parameter (no typed errors). The error is always `Throwable`, which loses domain-specific error information and doesn't enable exhaustive `when` matching.
- **Arrow's `Either<E, A>`:** Full-featured functional error handling with monad comprehensions. Powerful but heavy dependency for a focused use case. `Result<D, E>` provides the core benefit (typed errors) without the functional programming overhead.
- **Throwing exceptions with documentation:** Standard Kotlin approach. Doesn't provide compile-time guarantees that errors are handled. Easy to miss error cases, especially as the codebase grows.
