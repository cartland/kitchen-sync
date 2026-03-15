# Shopping List

Consolidated grocery lists with smart merging, pantry management, and checkout flow.

## Goal

- One active shopping list at a time per household
- Generate a consolidated list from selected recipes
- No ingredient merging: each ingredient line from a recipe becomes its own shopping list item
- Manual items: users can add items to the shopping list that aren't from recipes
- Each line cites its source recipe (if generated from a recipe) so you know where ingredients will be used
- Built-in database of common ingredients maps to grocery categories; category assignment is optional (uncategorized items are fine)
- Persistent pantry: maintain a list of staples (rice, salt, oil, etc.) that are auto-excluded from shopping lists
- Confirm remaining items you have at home
- Checkable list: tap items to check them off while shopping, state persists during the trip
- Checked items gray out in place (strikethrough); can be unchecked during shopping
- Archive checked items: removes them from list view (archived boolean + archived timestamp)
- Copy export of remaining (unchecked) items grouped by grocery category (produce, dairy, meat, etc.)
- Shared household pantry: one staples list per household, all members can view and edit
- Two-section UI: collapsible Pantry header and Shopping List header with distinct background colors
- Shopping list items show meal date badge linking to source recipe and meal plan day
- Pantry items have "+" to manually add to shopping list

## Status

Not started.

## Proposed

## Deferred

## Not Doing

- Grocery ordering automation — *Reason: the human places the order manually*
- Smart ingredient merging — *Reason: keep items separate; no parsing of "2 cloves garlic" vs "1 head garlic"*
- Multiple shopping lists (e.g., per store) — *Reason: one active list is sufficient*

## Open Questions
