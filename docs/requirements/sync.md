# Offline & Sync

Full offline functionality with cloud sync.

## Goal

- Full offline functionality: browse recipes, create/edit plans, modify shopping lists, check off items
- Changes sync to cloud backend when connectivity is restored
- Real-time sync: when online, changes appear instantly via Firestore listeners
- **MVP sync model: last-write-wins** — concurrent edits are resolved by accepting the latest write. The revision model (`basedOnRevision` on RecipeRevision) is retained as the target architecture for conflict detection, but conflict detection and resolution UI are deferred.
- Hard delete for MVP (soft delete with trash deferred)
- Account deletion + full data export as JSONL

## Status

Not started.

## Proposed

## Deferred

- **Conflict detection and resolution UI**: Optimistic concurrency with "mine vs theirs" picker — deferred; last-write-wins for MVP. The revision model is in place to support this later.
- **Soft delete with 30-day trash**: All user-deletable entities get `deleted` flag and trash browsing — deferred; hard delete only for MVP.
- **Per-field conflict granularity**: Conflict detection at the field level rather than entity level — deferred along with conflict resolution.

## Not Doing

## Open Questions
