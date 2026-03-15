# ADR-003: Local Database — Room (SQLite)

## Status

Accepted (2026-03-15)

## Context

Kitchen Sync is offline-first: the app must be fully functional without network connectivity. This requires a local database for recipes, meal plans, and grocery lists. The database must:

- Support complex queries (filtering, sorting, searching recipes)
- Work within KMP shared code (or have a KMP-compatible API)
- Handle schema migrations as the data model evolves

## Decision

Use **Room** (backed by SQLite) as the local database.

Room 2.7+ supports Kotlin Multiplatform, allowing database code to live in the shared KMP module. On iOS, Room uses a SQLite driver under the hood.

## Consequences

- **Positive:** Room is mature, well-documented, and widely used in the Android ecosystem. Known failure modes and solutions.
- **Positive:** Full SQL query support enables complex recipe searches, filtered grocery lists, and flexible data access.
- **Positive:** Room 2.7+ KMP support means database entities, DAOs, and queries are shared across platforms.
- **Positive:** Compile-time SQL verification catches query errors early.
- **Positive:** Built-in migration support for schema evolution.
- **Negative:** Room's KMP support (2.7+) is relatively new. Edge cases or platform-specific quirks may surface.
- **Negative:** SQLite on iOS is less idiomatic than Core Data or SwiftData, though this is hidden behind the Room abstraction.
- **Negative:** Requires careful schema design upfront since migrations can be complex.

## Alternatives Considered

- **SQLDelight:** KMP-native SQL library with longer multiplatform track record. Strong option, but Room's broader ecosystem, Google backing, and familiar API for Android developers tipped the decision.
- **Realm:** Object-oriented database with built-in sync. However, sync model doesn't align with our conflict-detection requirements, and Realm's future direction is uncertain after MongoDB acquisition changes.
- **Core Data (iOS) + Room (Android):** Platform-native databases. Duplicates the data layer and prevents sharing database code in KMP.
