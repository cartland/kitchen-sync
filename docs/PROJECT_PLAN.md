# Kitchen Sync — Project Plan

A staged plan for building Kitchen Sync. Earlier stages are detailed; later stages are intentional placeholders to expand when earlier work completes.

**Key principle:** each stage's detailed planning happens during or just before that stage, not all upfront.

## Dependency Map

```
Stage 0: Foundation (specification — resolve blocking questions)
   ↓
Stage 1: Project Bootstrap (KMP skeleton, CI, Firebase)
   ↓
Stage 2: Core Data Layer  ←→  Stage 2.5: Auth & Household (parallel)
   ↓
Stage 3: Recipes (first milestone part 1)
   ↓
Stage 4: Meal Planning (first milestone part 2)
   ↓
Stages 5–10: Future (placeholders — expand when ready)
```

## Stage 0: Foundation (Specification)

Resolve open questions and make scaffolding decisions that block implementation.

### Work Items

- Resolve snapshot vs live recipe references — blocks data layer design
- Resolve first-milestone UX questions — only those blocking Stages 3–4
- Project scaffolding decisions: KMP template, Gradle setup, repo module structure, linter/formatter, Firebase dev environment
- Version field mechanism for sync conflict detection

### Exit Criteria

- All blocking questions documented with decisions logged in [decisions.md](requirements/decisions.md)
- Scaffolding decisions recorded (KMP template, module layout, build tooling)

## Stage 1: Project Bootstrap (Implementation)

Stand up the project skeleton so all later stages have something to build on.

### Work Items

- KMP project skeleton with modules matching [ADR-005](architecture/adr-005-clean-architecture-layers.md) (domain/usecase/data/presentation)
- Dependencies: Room 2.7+, kotlin-inject, Firebase SDK, Compose, testing libs
- CI pipeline (GitHub Actions): build, test, lint, format
- Firebase project with Auth + Firestore enabled

### Exit Criteria

- App launches on both Android and iOS
- CI pipeline green on PR

## Stage 2: Core Data Layer (Implementation)

Build domain models, repositories, and storage — the foundation everything else depends on.

### Work Items

- Domain models for all 12 entities from [data-model.md](requirements/data-model.md)
- `Result<D, E>` sealed type per [ADR-007](architecture/adr-007-result-type-error-handling.md)
- Repository interfaces
- Room entities, DAOs, migrations
- Firestore data sources and security rules
- Repository implementations (offline-first: Room → Firestore sync)
- Basic conflict detection skeleton

### Exit Criteria

- Repositories pass integration tests with Room + fake Firestore

## Stage 2.5: Auth & Household (Can Overlap with Stage 2)

Authentication and the household shell — needs only domain models from Stage 2, not full repositories.

### Work Items

- Google sign-in (Firebase Auth) on Android and iOS
- Auto-create default household on first sign-in
- Active household state persistence
- Navigation shell with 3 bottom tabs (placeholder content)
- Profile menu dropdown

### Exit Criteria

- User can sign in on both platforms
- Default household exists after first sign-in
- Tab bar and profile menu render correctly

## Stage 3: Recipes (First Milestone Part 1)

Full recipe CRUD — the first user-visible feature.

### Sub-Discovery (resolve before implementation)

- Confirm Markdown rendering approach
- Smart paste spec (if needed for first milestone)

### Work Items

- Use cases: CRUD, search, rate, soft delete/restore, related recipes
- UI (Android + iOS in parallel): Recipe List, Detail, Editor, Trash
- Search: fuzzy across all fields, contextual metadata in results
- See [recipes.md](requirements/recipes.md) and [user-flows.md](requirements/user-flows.md) for full requirements

### Exit Criteria

- Full recipe CRUD works offline with sync
- Matches flows documented in [user-flows.md](requirements/user-flows.md)

## Stage 4: Meal Planning (First Milestone Part 2)

Meal scheduling and basic shopping list — completes the first milestone.

### Sub-Discovery (resolve before implementation)

- Default launch tab
- AI Suggest placement (Stage 4 or defer to Stage 8)
- Generate Shopping List trigger UX

### Work Items

- Use cases: add/remove/reschedule meals, create snapshots, generate shopping list
- UI: infinite scrolling timeline, recipe picker bottom sheet, meal item accordion
- Basic shopping list generation (ingredient consolidation, pantry exclusion)
- See [meal-planning.md](requirements/meal-planning.md) and [shopping.md](requirements/shopping.md)

### Exit Criteria

- First milestone complete: create recipes, plan meals, generate shopping list
- Works offline with sync

## Stages 5–10: Future (Placeholders)

These stages are deliberately vague. Expand each one when Stages 0–4 are complete and priorities are clearer.

| Stage | Area | Expand When |
|-------|------|-------------|
| 5 | Shopping (full UI, archive, export) | Stage 4 complete |
| 6 | Sync & conflict resolution (hardening, conflict UI) | Stage 4 complete |
| 7 | Household management (invites, roles, member detail, settings) | Stage 4 complete |
| 8 | AI features (recipe formatting, meal plan generation, AiEngine) | Stage 4 complete |
| 9 | Calendar integration (Google Calendar API) | Stage 4 complete |
| 10 | History & variety tracking | Stage 4 complete |

## Open Questions → Stage Blockers

| Question | Blocks | Source |
|----------|--------|--------|
| ~~Snapshot vs live recipe references~~ | ~~Stage 2, 4~~ | ~~kitchen-sync-qdl~~ — **Resolved**: revision model replaces snapshots |
| Version field for non-recipe entities | Stage 2 | [data-model.md](requirements/data-model.md) — recipes use revision model; other entities TBD |
| Default launch tab | Stage 4 | kitchen-sync-46u |
| AI Suggest placement | Stage 4 or 8 | kitchen-sync-46u |
| Generate Shopping List trigger | Stage 4 | kitchen-sync-46u |
| Shopping list lifecycle | Stage 5 | kitchen-sync-46u |
| Calendar integration surface | Stage 9 | kitchen-sync-46u |
| Meal Plan history browsing | Stage 10 | kitchen-sync-46u |

## Cross-Cutting Concerns (Every Stage)

- **Testing pyramid**: unit → integration → E2E per stage
- **Dark mode**: supported from day one
- **Offline-first**: verify with airplane mode each stage
- **Documentation**: update Status sections in requirements files as features complete

## Parallelism Rules

- **Stages 0 → 1**: strictly sequential
- **Stages 2 and 2.5**: can overlap (2.5 needs domain models from early Stage 2 only)
- **Android and iOS UI**: always parallelizable within any stage
- **Stages 5–10**: mostly independent, can be reordered by priority
