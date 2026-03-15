# Offline & Sync

Full offline functionality with cloud sync and conflict resolution.

## Committed

- Full offline functionality: browse recipes, create/edit plans, modify shopping lists, check off items
- Changes sync to cloud backend when connectivity is restored
- Real-time sync: when online, changes appear instantly via Firestore listeners
- Conflict handling: optimistic concurrency — prevent second write from clobbering first
  - Show error on conflict
  - Keep unsaved data available in the UI
  - Offer a button to download unsaved data as a local file for manual recovery
- Conflict resolution UI: show both versions, user picks "mine" or "theirs" (no manual merge)
- Conflict granularity: per-field (not per-entity or per-document)
- Soft delete with 30-day trash for all user-deletable entities
- Trash visibility: all household members can see and restore trashed items
- Account deletion + full data export as JSONL

## Proposed

## Deferred

## Not Doing

## Open Questions
