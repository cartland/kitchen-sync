# Cross-cutting Decision Log

Decisions that span multiple features or don't belong to a single feature area.

| Decision | Rationale | Date |
|----------|-----------|------|
| Google sign-in over other auth | Aligns with Google Calendar integration, which is a core feature | 2026-03-14 |
| No URL import for recipes | Manual entry only for now; keeps scope focused | 2026-03-14 |
| No grocery ordering automation | Human places orders manually; avoids integration complexity | 2026-03-14 |
| No recipe tags/categories | Flat list is sufficient for now; can revisit as recipe count grows | 2026-03-14 |
| Platform: KMP (Android + iOS) | Shared business logic in Kotlin, native UI per platform (Compose/SwiftUI) | 2026-03-15 |
| Backend: Firebase/Firestore | Managed backend, built-in auth, real-time sync, aligns with Google sign-in | 2026-03-15 |
| Local DB: Room (SQLite) | Mature, KMP support via Room 2.7+, full SQL for complex queries | 2026-03-15 |
| Sync: Conflict detection + user resolution | Per requirements: detect conflicts, show error, let user resolve. No silent LWW | 2026-03-15 |
| Module structure: Clean Architecture | domain → usecase → data → presentation layers with strict dependency rule | 2026-03-15 |
| DI: kotlin-inject | Compile-time, KMP-native, type-safe, no reflection | 2026-03-15 |
| Error handling: Result\<D, E\> sealed type | Never throw (except CancellationException), exhaustive when expressions | 2026-03-15 |
| CI: GitHub Actions | Build, test, lint, format check on every PR from day one | 2026-03-15 |
| First milestone: Recipes + meal plan | Core loop: enter recipes, plan meals for the week | 2026-03-15 |
