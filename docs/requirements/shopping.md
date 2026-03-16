# Shopping List

Consolidated grocery lists with checkout flow.

## Goal

- One active shopping list at a time per household
- Generate a consolidated list from selected recipes
- No ingredient merging: each ingredient line from a recipe becomes its own shopping list item
- Manual items: users can add items to the shopping list that aren't from recipes
- Each line cites its source recipe (if generated from a recipe) so you know where ingredients will be used
- Built-in database of common ingredients maps to grocery categories; category assignment is optional (uncategorized items are fine)
- Checkable list: tap items to check them off while shopping, state persists during the trip
- Checked items gray out in place (strikethrough), sink below unchecked items; can be unchecked during shopping
- Copy export of remaining (unchecked) items grouped by grocery category (produce, dairy, meat, etc.)
- Shopping list items show meal date badge linking to source recipe and meal plan day
- Tapping an item copies its name to the clipboard for pasting into external shopping apps

## Status

Not started.

## Proposed

## Deferred

- **Pantry management**: Persistent list of staples (rice, salt, oil, etc.) auto-excluded from shopping lists; shared per household; two-section UI (Pantry + Shopping List) — deferred; all ingredients appear in shopping list for MVP
- **Pantry item promotion**: Tap pantry item to add to shopping list as manual entry — deferred along with pantry

## Not Doing

- Grocery ordering automation — *Reason: the human places the order manually*
- Smart ingredient merging — *Reason: keep items separate; no parsing of "2 cloves garlic" vs "1 head garlic"*
- Multiple shopping lists (e.g., per store) — *Reason: one active list is sufficient*

## Open Questions
