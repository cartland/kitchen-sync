# ADR-004: Sync — Conflict Detection with User Resolution

## Status

Accepted (2026-03-15)

## Context

Kitchen Sync supports shared households where multiple users can edit the same data (recipes, meal plans, grocery lists) simultaneously. When two users modify the same entity while offline or concurrently, the system must handle the conflict.

The product requirements explicitly state: detect conflicts, show an error to the user, and let them resolve it. The system should not silently discard changes.

## Decision

Implement **conflict detection with user resolution**:

1. **Detection:** Each entity carries a version or timestamp. When syncing, the client compares its base version against the server's current version. If they differ (i.e., someone else modified the entity since the client last read it), a conflict is detected.
2. **User resolution:** When a conflict is detected, the app presents both versions to the user and lets them choose which to keep (or merge manually).
3. **No silent overwrites:** The system never applies last-writer-wins silently. Users always see when their changes conflict with someone else's.

## Consequences

- **Positive:** No data loss. Users are always aware of conflicts and can make informed decisions about resolution.
- **Positive:** Simpler to implement correctly than automatic merge strategies. The conflict UI is straightforward: show both versions, let user pick.
- **Positive:** Aligns with the product requirement that conflicts are surfaced as errors, not hidden.
- **Negative:** Requires UI for conflict resolution on both platforms (Android and iOS).
- **Negative:** Frequent conflicts could be annoying in high-contention scenarios (e.g., two users editing the same grocery list simultaneously). In practice, household meal planning has low contention.
- **Negative:** Version tracking adds complexity to the data model and sync logic.

## Alternatives Considered

- **Last-Writer-Wins (LWW):** Simplest to implement, but silently discards one user's changes. Unacceptable per product requirements — users must know when data is overwritten.
- **CRDTs (Conflict-free Replicated Data Types):** Automatic conflict resolution with mathematical guarantees. Attractive in theory but significantly more complex to implement, harder to reason about for structured data like recipes, and overkill for a household app with low contention.
- **Operational Transform (OT):** Used by collaborative editors (Google Docs). Far too complex for this domain and data model.
