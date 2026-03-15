# Offline & Sync

Full offline functionality with cloud sync and conflict resolution.

## Committed

- Full offline functionality: browse recipes, create/edit plans, modify shopping lists, check off items
- Changes sync to cloud backend when connectivity is restored
- Conflict handling: optimistic concurrency — prevent second write from clobbering first
  - Show error on conflict
  - Keep unsaved data available in the UI
  - Offer a button to download unsaved data as a local file for manual recovery
- Conflict resolution UI: show both versions, user picks "mine" or "theirs" (no manual merge)
- Soft delete with 30-day trash for all user-deletable entities
- Account deletion + full data export (recipes, plans as JSON/files)

## Proposed

## Deferred

## Not Doing

## Open Questions
