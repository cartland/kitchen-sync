# User Flows & Screens

Map of the app: every screen, how they connect, and the key user journeys. This file bridges the per-feature requirements files by showing how features compose into end-to-end experiences.

## Goal

### Navigation

- **Bottom tab bar** with three tabs: **Recipes**, **Meal Plan**, **Shopping List**
- **Profile icon** (top-right) opens a dropdown menu: Switch Household, Manage Household, Settings, Sign Out
- Default launch tab: Recipes (first launch) / last-used tab (returning user)

### Screen Inventory

#### Authentication & Household

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 1 | Sign-In | Google sign-in entry point | Google sign-in button | → Recipe List (on success, first launch) or last-used tab (returning user) |
| 2 | Household Switcher | Dropdown from profile menu; lists joined households + "Create New" option | List of joined households, "Create New" option | → selected household context; → Household creation flow |
| 3 | Manage Household | Member list with roles; invite link generation (Admin only) | Member list with role badges (Admin/Member), Generate Link action (Admin only), copyable invite link with 1-day expiry indicator, Revoke link (Admin only) | → Member Detail (tap member) |
| 4 | Member Detail | Role-based options: Admins see remove/change role; Members see leave | Role badge; Admin sees: Change Role, Remove Member; Member sees: Leave Household | → Manage Household (back); confirmation dialogs for destructive actions |
| 5 | Settings | Dark mode (default: match system), data export (JSONL), account deletion | Dark mode toggle (default: system), Data Export button (JSONL), Delete Account button | → Export confirmation; → Delete Account confirmation dialog |

#### Recipes (Tab 1)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 6 | Recipe List | Search bar at top; "Add Recipe" card as first item (hidden during search); recipe cards below | Search bar, "Add Recipe" card (first item, hidden during search), recipe cards (title + metadata) | → Recipe Detail (tap card); → Recipe Editor (tap Add Recipe); → Trash (via search results) |
| 7 | Recipe Detail | Full-screen rendered view; title row with 5-star rating (average + count) on top-right; edit button; conflict banner when applicable | Title, 5-star rating (avg + count, tappable), sections (Intro, Ingredients, Preparation, Cooking) in rendered Markdown, Edit button, Conflict banner (conditional) | → Recipe Editor (tap Edit); → Conflict Resolution (tap Resolve on banner) |
| 8 | Recipe Editor | Section placeholders (Title, Intro, Ingredients, Preparation, Cooking) styled in gray; rich text fields in edit mode with formatting toolbar (bullets, bold); smart paste; AI restructure button | Section fields with gray placeholders (Title, Intro, Ingredients, Preparation, Cooking), formatting toolbar (bullets, bold), AI Restructure button, Save/Cancel | → Recipe Detail (Save); → back to previous screen (Cancel); → AI Restructure Review (tap AI Restructure) |
| 9 | Trash | Accessed via search results; recipe cards in distinct colors; restore option; 30-day retention | Deleted recipe cards (distinct color), Restore button per card, 30-day retention indicator | → Recipe List (Restore); → read-only Recipe Detail with Restore action only (tap card) |

#### Meal Planning (Tab 2)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 10 | Meal Plan Timeline | Infinite scrolling timeline anchored to today (future below, past above); day dividers with day-of-week + date; "+" button per day; meals listed under their day | Day dividers (day-of-week + date), "+" button per day, meal cards with archive status icon (empty circle = none archived, half circle = some archived, full circle = all archived), today anchor | → Recipe Picker (tap "+"); → Meal Item expand (tap meal) |
| 11 | Meal Item (expanded) | Inline accordion (one at a time) on a meal in the timeline | Default: View button, Edit button. Edit mode: Archive (or Restore if archived), Date, Delete | → Recipe Detail (View); → Date Picker (Date); → Delete Undo Placeholder (Delete) |
| 12 | Recipe Picker | Bottom sheet with search bar on top; random recipe suggestion | Bottom sheet, search bar, recipe list, random recipe suggestion | → Timeline with new meal (select recipe); → dismiss (swipe down / tap outside) |
| 13 | Date Picker | Date picker opened from expanded meal item's "Date" option | Calendar/date selector, confirm/cancel | → Timeline (confirm, meal rescheduled); → Meal Item (cancel) |
| 14 | Delete Undo Placeholder | Temporary placeholder in deleted meal's slot with Undo option | Placeholder card in deleted meal's slot, Undo button, auto-dismiss timer | → Timeline restored (Undo); → Timeline without meal (timeout/dismiss) |

#### Shopping (Tab 3)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 15 | Shopping Tab | Computed fresh from meal plan; two sections: collapsed Pantry category and Shopping List | Pantry section header (collapsed, contains pantry-matched ingredients), Shopping List section header (expanded) | → Pantry Section (expand); → Shopping List Section (expand) |
| 16 | Pantry Section | Collapsed list of ingredients matching pantry staples; tap an item to promote it to shopping list as manual entry | Pantry-matched ingredient list, tap to promote to shopping list; add new pantry staple field | → Shopping List (item promoted on tap) |
| 17 | Shopping List Section | Computed from meal plan (excluding pantry items); tap item to copy name to clipboard; checked items sink below unchecked | Checklist items (checkbox left, name+quantity center, meal date badge right), "+" at bottom for manual items, checked items: gray strikethrough below unchecked items (ordered by addition order within each group, most recently checked on top of checked group) | → clipboard copy (tap item name); → Meal Plan day (tap badge → expand source detail → tap link) |

#### System (overlay / contextual)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 18 | Conflict Banner | Top banner on Recipe Detail when conflict detected; warning icon appears on recipe references everywhere; local editing allowed but sync to cloud blocked | Top banner on Recipe Detail, warning icon, explanation text, Resolve button; warning icon also shown on recipe references elsewhere | → Conflict Resolution (tap Resolve) |
| 19 | Conflict Resolution | Side-by-side "mine vs theirs" whole-recipe picker; copy button for either version's Markdown | Side-by-side "mine" vs "theirs" display, "Use Mine" / "Use Theirs" selection, Copy button per version (copies full Markdown) | → Recipe Detail (selection made; resolving with "theirs" discards local changes; resolving with "mine" submits with current cloud revision ID) |
| 20 | AI Restructure Review | Full-screen review of AI-reformatted recipe content before saving | Full-screen preview of reformatted Markdown sections, Approve/Cancel | → Recipe Editor with reformatted content (Approve); → Recipe Editor unchanged (Cancel) |
| 21 | Profile Menu | Dropdown from top-right profile icon: Switch Household, Manage Household, Settings, Sign Out | Dropdown: Switch Household, Manage Household, Settings, Sign Out | → Household Switcher; → Manage Household; → Settings; → Sign-In (Sign Out) |

### Role-Based UI Elements

Certain UI controls are only visible to household Admins:

- **Manage Household**: Generate Link action, Revoke link, Remove Member action, Change Role action
- **Member Detail**: Remove Member option (Admin viewing another member)

Members see Leave Household instead of Remove on their own Member Detail screen. If the last Admin leaves, remaining Members are auto-promoted to Admin.

### Shopping List Model

The shopping list is **computed fresh from the meal plan** each time the Shopping tab is opened:

1. **Ingredients from all non-archived meal plan entries** are aggregated into the shopping list
2. **Pantry-matched ingredients** are grouped under the collapsed Pantry section header instead of appearing in the shopping list
3. **Archiving a meal plan entry** removes its ingredients from the shopping list calculation; any affected checked items are unchecked (since totals changed)
4. **Restoring an archived meal plan entry** (via Edit → Restore on the meal item) re-adds its ingredients to the shopping list
5. **Archiving is per meal plan entry** — adding the same recipe to a new day creates a fresh set of ingredients
6. **Checked items** sink below unchecked items; order within each group is determined by the order items were added (most recently checked item on top of checked group)
7. **Tapping an item** copies its name to the clipboard for pasting into external shopping apps

### Empty States

- **Recipe List**: "Add Recipe" card (always first item) followed by gray ghost/placeholder cards in the same shape as recipe cards, implying what the list will look like
- **Meal Plan Timeline**: Days shown with "+" buttons; minimum height per day; no ghost cards needed since the timeline structure is self-explanatory
- **Shopping List**: "Add recipes to your meal plan" message with empty ruled lines matching the dividers between list items; Pantry and Shopping List headers still visible

### Core User Flows

#### 1. First Launch / Onboarding

> User goal: Get started with the app and set up their first meal plan.

1. **Sign-In** — User signs in with Google
2. Default household created automatically (unnamed)
3. **Recipe List** (Recipes tab) — Empty state with ghost cards and "Add Recipe" card
4. Tap "Add Recipe"
5. **Recipe Editor** — Section placeholders guide the user; paste or type recipe
6. Save → **Recipe Detail** — User sees their first recipe rendered
7. Switch to **Meal Plan** tab → tap "+" on a day
8. **Recipe Picker** (bottom sheet) — Select the recipe just created

#### 2. Add Recipe (Manual)

> User goal: Add a new recipe to the household collection.

1. **Recipe List** — Tap "Add Recipe" card
2. **Recipe Editor** — Type into section placeholders (Title, Intro, Ingredients, Preparation, Cooking)
3. Save
4. **Recipe Detail** — Full-screen rendered view of the new recipe

#### 3. Add Recipe (Smart Paste)

> User goal: Quickly add a recipe from unstructured text.

1. **Recipe List** — Tap "Add Recipe" card
2. **Recipe Editor** — Paste text into Title field
3. If empty editor: first line becomes Title, rest goes to Intro
4. If format is recognized: sections auto-fill
5. Optionally tap AI Restructure button → **AI Restructure Review** (full-screen): AI reformats pasted text into restricted Markdown sections; user reviews the reformatted content and approves or cancels
6. Save → **Recipe Detail**

#### 4. Add Meal to Plan

> User goal: Add a recipe to a specific day.

1. **Meal Plan Timeline** — Scroll to desired day
2. Tap "+" on that day
3. **Recipe Picker** (bottom sheet) — Search or browse; a random recipe suggestion is shown
4. Select a recipe → meal added to the day
5. Timeline updates with the new meal listed under the day; meal's ingredients automatically appear in shopping list

#### 5. Manage Planned Meal

> User goal: View, reschedule, archive, or delete a planned meal.

1. **Meal Plan Timeline** — Tap a meal
2. **Meal Item** expands inline (accordion, one at a time) showing: View, Edit
3. **View** → opens **Recipe Detail** full-screen
4. **Edit** → reveals actions: Archive (or Restore if already archived), Date, Delete
5. **Archive** → meal's ingredients removed from shopping list; archive icon updates (empty → half/full circle)
6. **Restore** → meal's ingredients re-added to shopping list; checked items for affected ingredients are unchecked
7. **Date** → opens **Date Picker** to reschedule
8. **Delete** → meal deleted, **Undo Placeholder** appears in its slot

#### 6. AI-Assisted Meal Planning

> User goal: Get a complete weekly plan suggestion based on recipes, history, and ratings.

1. **Meal Plan Timeline** — Tap "AI Suggest"
2. AI proposes meals for the week (Prepare phase)
3. User reviews and edits the proposal (Review phase)
4. User approves → meals applied to timeline (Execute phase)

#### 7. Shopping Trip

> User goal: Check off items while shopping.

1. **Shopping Tab** — Shopping list is computed fresh from non-archived meal plan entries (pantry items grouped under collapsed Pantry section)
2. Check off items as purchased (gray strikethrough; checked items sink below unchecked)
3. Tap an item name to copy it to clipboard for pasting into external shopping apps (e.g., Safeway)
4. Tap meal date badge on any item → expands recipe name + meal plan dates → tap to jump to that day in Meal Plan

#### 8. Manage Pantry

> User goal: Configure staple items that should be excluded from shopping lists.

1. **Shopping Tab** — Expand Pantry section
2. Add staple items to the pantry
3. Pantry-matched ingredients from the meal plan appear under the Pantry section header instead of the shopping list
4. Tap a pantry item to promote it to the shopping list as a manual entry

#### 9. Invite Household Member

> User goal: Add someone to the household.

1. **Profile Menu** → Manage Household
2. **Manage Household** — Tap "Generate Link" (Admin only)
3. Invite link displayed inline with 1-day expiry indicator; tap to copy; Admin can revoke
4. Share link externally (message, email, etc.)
5. Recipient opens link → **Sign-In** (if needed) → joins household

#### 10. Switch Households

> User goal: View a different household's data.

1. **Profile Menu** → Switch Household
2. **Household Switcher** dropdown — Lists joined households + "Create New"
3. Select a household → app context changes, all tabs show selected household's data

#### 11. Resolve Sync Conflict

> User goal: Resolve a conflicting edit made by another household member.

1. Conflict detected on sync → local editing allowed but sync to cloud blocked on affected recipe
2. Warning icon appears next to recipe name wherever it is displayed
3. **Recipe Detail** — Conflict banner at top explains the issue
4. Tap "Resolve" → **Conflict Resolution** screen
5. Side-by-side view of "mine" vs "theirs" for the whole recipe; Copy button available for either version's full Markdown
6. User picks "Use Mine" or "Use Theirs" — "Use Mine" submits local changes with the current cloud revision ID; "Use Theirs" discards local changes
7. Return to **Recipe Detail** with resolved data, cloud sync re-enabled

#### 12. Rate a Recipe

> User goal: Record a star rating for a recipe.

1. **Recipe Detail** — Tap 5-star rating in top-right of title row
2. Select star rating (1-5)
3. Rating saved for this user; average and count update

#### 13. Restore Deleted Recipe

> User goal: Recover a recipe that was accidentally deleted.

1. **Recipe List** — Search for "Trash" (or see Trash as last result in any search)
2. **Trash** — Browse deleted recipe cards (distinct color)
3. Tap a recipe → read-only Recipe Detail with Restore action only
4. Restore → recipe reappears in the Recipe List

#### 14. Search Recipes

> User goal: Find a specific recipe.

1. **Recipe List** — Tap search bar
2. "Add Recipe" card disappears; type query
3. Fuzzy text search across all recipe fields
4. Results show recipe name + contextual metadata from matching sections (Ingredients, Preparation, Rating, etc.)
5. Trash always appears as last result (unless searching for "Trash" specifically, then it surfaces first)
6. Tap a result → **Recipe Detail**

## Status

Not started.

## Proposed

## Deferred

- **Recipe List filters**: Filter by rating, last cooked — deferred from initial release
- **Related Recipes links**: Bidirectional links between recipes on Recipe Detail — deferred from initial release
- **AI Recipe Picker suggestions**: AI-powered suggestions in Recipe Picker — deferred to AI stage
- **Invite Link Display screen**: Currently inline in Manage Household; may become its own screen if the flow grows more complex

## Not Doing

## Missing Screens (to define during sub-discovery)

- **Recipe Diff View**: Mentioned in recipes.md for comparing revisions; no screen defined yet. Needed for Stage 3.
- **AI Proposal Review**: ADR-010 Prepare/Review/Execute flow needs a review screen for AI-generated content. Needed for Stage 8.
- **Data Export Flow**: sync.md mentions JSONL export; no flow defined. Needed for Stage 6+.
- ~~**Invite Link Display**: identity.md describes invite generation; no screen for showing/sharing the link. Needed for Stage 7.~~ → Handled inline in Manage Household.
- **History Browser**: history.md references history browsing; no screen defined. Needed for Stage 10.

## Open Questions

- **Shopping list lifecycle**: ~~When is a shopping list considered "done"? Manual clear, or auto-clear when a new list is generated?~~ → Resolved: shopping list is computed fresh from non-archived meal plan entries. Archiving a meal plan entry removes its ingredients from the list. (Note: verify this model works for Stage 4 implementation)
- **Calendar integration surface**: In-app calendar view, or only push events to Google Calendar? Where does the user trigger sync?
- **AI Suggest placement**: Where does the "AI Suggest" button live on the Meal Plan Timeline?
- **Generate Shopping List trigger**: ~~Where does this action live — on the Meal Plan tab, the Shopping tab, or both?~~ → Resolved: shopping list is auto-computed when the Shopping tab is opened; no explicit trigger needed.
- **Meal Plan history**: How does the user browse past meal plans? Scroll up in the timeline, or a separate history view?
- **Default launch tab**: Should returning users land on last-used tab, Meal Plan (most actionable), or Recipes?
