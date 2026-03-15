# Data Model

Entity definitions for Kitchen Sync. All entities include `createdTimestamp` and `modifiedTimestamp` unless noted otherwise.

## Goal

### User

| Field | Type | Notes |
|-------|------|-------|
| userId | string | Internal app ID |
| googleId | string | Google account identifier |
| displayName | string | |
| email | string | |

### Household

| Field | Type | Notes |
|-------|------|-------|
| householdId | string | |
| name | string | Unnamed by default; required when sharing/inviting |

### Membership

| Field | Type | Notes |
|-------|------|-------|
| userId | string | FK → User |
| householdId | string | FK → Household |
| role | enum | Admin, Member |

### Invite Link

| Field | Type | Notes |
|-------|------|-------|
| token | string | Unique shareable token |
| householdId | string | FK → Household |
| createdBy | string | FK → User (Admin who generated) |
| expiresTimestamp | timestamp | 1-day expiry from creation |
| revoked | boolean | Admins can revoke before expiry |

### Recipe

| Field | Type | Notes |
|-------|------|-------|
| recipeId | string | |
| householdId | string | FK → Household |
| title | string | |
| intro | string | Description/notes between title and ingredients |
| ingredients | list\<Ingredient\> | Structured list (see below) |
| preparation | string | Restricted Markdown (headers, bullets, bold) |
| cooking | string | Restricted Markdown |
| createdBy | string | FK → User |
| modifiedBy | string | FK → User |
| deleted | boolean | Soft delete flag |
| deletedTimestamp | timestamp | Null when not deleted; 30-day retention |

### Ingredient

Sub-entity of Recipe (embedded in the ingredients list).

| Field | Type | Notes |
|-------|------|-------|
| quantity | string | Freeform (e.g., "2", "1/2", "a pinch") |
| unit | string? | Nullable — empty unit allowed (e.g., "2 eggs") |
| name | string | Ingredient name |

### Related Recipe Link

| Field | Type | Notes |
|-------|------|-------|
| recipeId1 | string | FK → Recipe |
| recipeId2 | string | FK → Recipe |

Bidirectional — order does not matter.

### Recipe Snapshot

Full copy of recipe content frozen at a point in time. Captured when a recipe is assigned to a meal plan.

| Field | Type | Notes |
|-------|------|-------|
| snapshotId | string | |
| recipeId | string | FK → Recipe (source) |
| revisionTimestamp | timestamp | When the snapshot was captured |
| title | string | |
| intro | string | |
| ingredients | list\<Ingredient\> | |
| preparation | string | |
| cooking | string | |

> **Open (kitchen-sync-qdl):** Review when entities reference the live recipe vs. a snapshot. Meal plan entries use snapshots, but how do shopping list items connect — to the live recipe, the meal plan entry, or the snapshot? See bead for full context.

### Meal Plan Entry

| Field | Type | Notes |
|-------|------|-------|
| entryId | string | |
| householdId | string | FK → Household |
| date | date | Calendar date for the meal |
| displayOrder | int | Order within the day (0-indexed) |
| recipeId | string | FK → Recipe (live reference) |
| snapshotId | string | FK → Recipe Snapshot |

### Rating

No `modifiedTimestamp` — ratings are replaced, not edited.

| Field | Type | Notes |
|-------|------|-------|
| userId | string | FK → User |
| recipeId | string | FK → Recipe |
| stars | int | 1–5 |
| timestamp | timestamp | When the rating was given/updated |

### Shopping List Item

| Field | Type | Notes |
|-------|------|-------|
| itemId | string | |
| householdId | string | FK → Household |
| name | string | Ingredient name |
| quantity | string | |
| unit | string? | Nullable |
| checked | boolean | Checkbox state |
| archived | boolean | Archived items hidden from list view |
| archivedTimestamp | timestamp? | Null when not archived |
| sourceEntryId | string? | FK → Meal Plan Entry (null for manual items) |

### Pantry Item

| Field | Type | Notes |
|-------|------|-------|
| itemId | string | |
| householdId | string | FK → Household |
| name | string | Staple ingredient name |

### Entity Relationship Summary

```
User ──┬── Membership ──── Household
       │                      │
       ├── Rating ────────────┼── Recipe ──── Ingredient (embedded)
       │                      │     │
       └── Invite Link ───────┘     ├── Related Recipe Link
                                    │
                              Recipe Snapshot
                                    │
                              Meal Plan Entry
                                    │
                              Shopping List Item

                              Pantry Item ──── Household
```

## Status

Not started.

## Proposed

## Deferred

## Not Doing

- Cook time, prep time, serving size fields on Recipe — *Reason: not needed; search filters removed accordingly*

## Open Questions

- **Snapshot vs. live recipe references**: When do meal plans and shopping lists reference the live recipe vs. a frozen snapshot? (tracked as kitchen-sync-qdl)
- **Ingredient quantity type**: String allows freeform input ("2", "1/2", "a pinch") but prevents math. Is this sufficient?
- **Version field for sync**: Entities likely need a `version` field for conflict detection (per ADR-004). Exact mechanism TBD during implementation.
