# Recipe Storage

Structured recipe management with versioning and snapshots.

## Goal

- Recipes stored as restricted Markdown (headers, bullets, bold emphasis only)
- Template sections: Title, Intro, Ingredients, Preparation, Cooking
- Serving size described in freeform text (not enforced, no automatic scaling)
- Ingredient units: typed sealed class with common metric and imperial units, plus a freeform text variant (freeform does not support math/scaling)
- Manual entry with AI-assisted formatting: paste freeform text → AI reformats into restricted Markdown → user reviews preview → approves to save (see `ai-features.md`)
- Any household member can edit any recipe (no per-recipe ownership)
- Related recipes: bidirectional links between any two recipes
- Recipes evolve over time; when used in a meal plan, the full content is snapshotted
- Diff view available on demand: compare a snapshot to the current version of a recipe
- Full search + filters: search by name and ingredients, filter by cook time, household rating, last cooked date

## Status

Not started.

## Proposed

## Deferred

## Not Doing

- URL import for recipes — *Reason: manual entry only for now*
- Tags/categories for recipes — *Reason: flat list of recipes is sufficient for now*
- Ingredient scaling by servings — *Reason: serving size is freeform text, no math*

## Open Questions
