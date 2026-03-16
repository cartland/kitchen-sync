# Recipe Storage

Structured recipe management with revision history.

## Goal

- Recipes stored as restricted Markdown (headers, bullets, bold emphasis only)
- Template sections: Title, Intro, Ingredients, Preparation, Cooking
- Ingredient units: typed sealed class with common metric and imperial units, plus a freeform text variant (freeform does not support math/scaling)
- Manual entry with AI-assisted formatting: paste freeform text → AI reformats into restricted Markdown → user reviews preview → approves to save (see `ai-features.md`)
- Any household member can edit any recipe (no per-recipe ownership)
- Recipes use a revision model: every edit creates a new immutable RecipeRevision with a monotonically increasing sequence number
- Meal plans reference a specific revision — no separate snapshot needed
- Diff view available on demand: compare any two revisions of a recipe
- All revisions kept permanently
- Conflict detection: edits include `basedOnRevision`; if it doesn't match `currentRevision`, last-write-wins for MVP (conflict resolution UI deferred)
- Full search by name and ingredients; rating and last-cooked filters deferred

## Status

Not started.

## Proposed

## Deferred

- Related recipes: bidirectional links between any two recipes — *Reason: deferred from MVP to reduce scope*
- Conflict detection: edits include `basedOnRevision`; if it doesn't match `currentRevision`, the user resolves the conflict — *Reason: MVP uses last-write-wins; revision model kept as architecture*
- Filter by household rating, last cooked date — *Reason: ratings deferred from MVP*

## Not Doing

- URL import for recipes — *Reason: manual entry only for now*
- Tags/categories for recipes — *Reason: flat list of recipes is sufficient for now*
- Ingredient scaling by servings — *Reason: no serving size field, no math*
- Serving size field — *Reason: not needed for MVP*
- Cook time / prep time fields — *Reason: not needed for MVP*

## Open Questions
