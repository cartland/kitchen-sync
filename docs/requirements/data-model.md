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
| currentRevision | int | Sequence number of the latest revision |
| createdBy | string | FK → User |
| deleted | boolean | Soft delete flag |
| deletedTimestamp | timestamp | Null when not deleted; 30-day retention |

User-visible content (title, intro, ingredients, preparation, cooking) lives in RecipeRevision.

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

### RecipeRevision

Immutable record of recipe content at a point in time. Every edit creates a new revision with an incremented sequence number. All revisions are kept permanently.

| Field | Type | Notes |
|-------|------|-------|
| recipeId | string | FK → Recipe |
| revision | int | Monotonically increasing sequence number (composite key with recipeId) |
| title | string | |
| intro | string | Description/notes between title and ingredients |
| ingredients | list\<Ingredient\> | |
| preparation | string | Restricted Markdown (headers, bullets, bold) |
| cooking | string | Restricted Markdown |
| editedBy | string | FK → User |
| editedAt | timestamp | When this revision was created |
| basedOnRevision | int | The revision the author was viewing when editing; used for conflict detection |

**Conflict detection:** When submitting an edit, the client sends `basedOnRevision`. If it matches the recipe's `currentRevision`, the server writes a new revision. If not, a local conflict record is created for the user to resolve (pick mine vs theirs).

**Snapshots are just revision pointers.** Meal plan entries reference a specific `recipeId` + `revision` — no separate snapshot entity needed.

### Meal Plan Entry

| Field | Type | Notes |
|-------|------|-------|
| entryId | string | |
| householdId | string | FK → Household |
| date | date | Calendar date for the meal |
| displayOrder | int | Order within the day (0-indexed) |
| recipeId | string | FK → Recipe |
| revision | int | FK → RecipeRevision (the revision at time of planning) |

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
       ├── Rating ────────────┼── Recipe ──── RecipeRevision ──── Ingredient (embedded)
       │                      │     │
       └── Invite Link ───────┘     ├── Related Recipe Link
                                    │
                              Meal Plan Entry (references recipeId + revision)
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

- **Ingredient quantity type**: String allows freeform input ("2", "1/2", "a pinch") but prevents math. Is this sufficient?
- **Ingredient unit type**: The `unit` field is stored as a nullable string in the data model, but [recipes.md](recipes.md) specifies a "typed sealed class with common metric and imperial units, plus a freeform text variant." Clarify whether the sealed class is a domain-layer abstraction over string storage or if the data model needs a richer type.
- **Version field for non-recipe entities**: Recipes use the revision model for conflict detection. Other entities (meal plan entries, shopping list items, etc.) may still need a version field per ADR-004. Exact mechanism TBD during implementation.

## Resolved Questions

- **Snapshot vs. live recipe references** (kitchen-sync-qdl): Resolved — replaced Recipe Snapshot entity with RecipeRevision model. Meal plan entries reference a specific `recipeId` + `revision`. Shopping list items connect through the meal plan entry's revision pointer. No separate snapshot entity needed. All revisions kept permanently.
