# User Flows & Screens

Map of the app: every screen, how they connect, and the key user journeys. This file bridges the per-feature requirements files by showing how features compose into end-to-end experiences.

## Goal

### Navigation

- **Bottom tab bar** with three tabs: **Recipes**, **Meal Plan**, **Shopping List**
- **Profile icon** (top-right) opens a dropdown menu: Manage Household, Settings, Sign Out
- Default launch tab: Recipes (first launch) / last-used tab (returning user)

### Screen Inventory

#### Authentication & Household

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 1 | Sign-In | Google sign-in entry point | Google sign-in button | → Recipe List (on success, first launch) or last-used tab (returning user) |
| 2 | Manage Household | Member list; invite link generation (owner only) | Member list, Generate Link action (owner only), copyable invite link | → Member Detail (tap member) |
| 3 | Member Detail | Owner sees member info; members see Leave Household | Owner sees: member info. Member sees: Leave Household | → Manage Household (back); confirmation dialog for Leave Household |
| 4 | Settings | Dark mode toggle | Dark mode toggle (default: system) | (stays on Settings) |

#### Recipes (Tab 1)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 5 | Recipe List | Search bar at top; "Add Recipe" card as first item (hidden during search); recipe cards below | Search bar, "Add Recipe" card (first item, hidden during search), recipe cards (title + metadata) | → Recipe Detail (tap card); → Recipe Editor (tap Add Recipe) |
| 6 | Recipe Detail | Full-screen rendered view; title row; edit button | Title, sections (Intro, Ingredients, Preparation, Cooking) in rendered Markdown, Edit button | → Recipe Editor (tap Edit) |
| 7 | Recipe Editor | Section placeholders (Title, Intro, Ingredients, Preparation, Cooking) styled in gray; rich text fields in edit mode with formatting toolbar (bullets, bold); smart paste; AI restructure button | Section fields with gray placeholders (Title, Intro, Ingredients, Preparation, Cooking), formatting toolbar (bullets, bold), AI Restructure button, Save/Cancel | → Recipe Detail (Save); → back to previous screen (Cancel); → AI Restructure Review (tap AI Restructure) |

#### Meal Planning (Tab 2)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 8 | Meal Plan Timeline | Infinite scrolling timeline anchored to today (future below, past above); day dividers with day-of-week + date; "+" button per day; meals listed under their day | Day dividers (day-of-week + date), "+" button per day, meal cards, today anchor | → Recipe Picker (tap "+"); → Meal Item expand (tap meal) |
| 9 | Meal Item (expanded) | Inline accordion (one at a time) on a meal in the timeline | Default: View button, Edit button. Edit mode: Date, Delete | → Recipe Detail (View); → Date Picker (Date); → Delete Undo Placeholder (Delete) |
| 10 | Recipe Picker | Bottom sheet with search bar on top; random recipe suggestion | Bottom sheet, search bar, recipe list, random recipe suggestion | → Timeline with new meal (select recipe); → dismiss (swipe down / tap outside) |
| 11 | Date Picker | Date picker opened from expanded meal item's "Date" option | Calendar/date selector, confirm/cancel | → Timeline (confirm, meal rescheduled); → Meal Item (cancel) |
| 12 | Delete Undo Placeholder | Placeholder in deleted meal's slot with Undo and X (dismiss) buttons | Placeholder card in deleted meal's slot, Undo button, X (dismiss) button | → Timeline restored (Undo); → Timeline without meal (dismiss) |

#### Shopping (Tab 3)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 13 | Shopping Tab | Computed fresh from meal plan; single shopping list section | Shopping list items (checkbox left, name+quantity center, meal date badge right), "+" at bottom for manual items, checked items: gray strikethrough below unchecked items | → clipboard copy (tap item name); → Meal Plan day (tap badge → expand source detail → tap link) |

#### System (overlay / contextual)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 14 | AI Restructure Review | Full-screen review of AI-reformatted recipe content before saving | Full-screen preview of reformatted Markdown sections, Approve/Cancel | → Recipe Editor with reformatted content (Approve); → Recipe Editor unchanged (Cancel) |
| 15 | Profile Menu | Dropdown from top-right profile icon: Manage Household, Settings, Sign Out | Dropdown: Manage Household, Settings, Sign Out | → Manage Household; → Settings; → Sign-In (Sign Out) |

### Role-Based UI Elements

The household has a single owner and members:

- **Owner only**: Generate Link action in Manage Household
- **Members**: see Leave Household on their own Member Detail screen

### Shopping List Model

The shopping list is **computed fresh from the meal plan** each time the Shopping tab is opened:

1. **Ingredients from all meal plan entries** are aggregated into the shopping list
2. **Deleting a meal plan entry** removes its ingredients from the shopping list
3. **Checked items** sink below unchecked items; order within each group is determined by the order items were added (most recently checked item on top of checked group)
6. **Tapping an item** copies its name to the clipboard for pasting into external shopping apps

### Empty States

- **Recipe List**: "Add Recipe" card (always first item) followed by gray ghost/placeholder cards in the same shape as recipe cards, implying what the list will look like
- **Meal Plan Timeline**: Days shown with "+" buttons; minimum height per day; no ghost cards needed since the timeline structure is self-explanatory
- **Shopping List**: "Add recipes to your meal plan" message with empty ruled lines matching the dividers between list items

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

> User goal: View, reschedule, or delete a planned meal.

1. **Meal Plan Timeline** — Tap a meal
2. **Meal Item** expands inline (accordion, one at a time) showing: View, Edit
3. **View** → opens **Recipe Detail** full-screen
4. **Edit** → reveals actions: Date, Delete
5. **Date** → opens **Date Picker** to reschedule
6. **Delete** → meal deleted, **Undo Placeholder** appears in its slot

#### 6. AI-Assisted Meal Planning

> User goal: Get a complete weekly plan suggestion based on recipes and history.

1. **Meal Plan Timeline** — Tap "AI Suggest"
2. AI proposes meals for the week (Prepare phase)
3. User reviews and edits the proposal (Review phase)
4. User approves → meals applied to timeline (Execute phase)

#### 7. Shopping Trip

> User goal: Check off items while shopping.

1. **Shopping Tab** — Shopping list is computed fresh from meal plan entries
2. Check off items as purchased (gray strikethrough; checked items sink below unchecked)
3. Tap an item name to copy it to clipboard for pasting into external shopping apps (e.g., Safeway)
4. Tap meal date badge on any item → expands recipe name + meal plan dates → tap to jump to that day in Meal Plan

#### 8. Invite Household Member

> User goal: Add someone to the household.

1. **Profile Menu** → Manage Household
2. **Manage Household** — Tap "Generate Link" (owner only)
3. Invite link displayed inline; tap to copy
4. Share link externally (message, email, etc.)
5. Recipient opens link → **Sign-In** (if needed) → joins household

#### 9. Search Recipes

> User goal: Find a specific recipe.

1. **Recipe List** — Tap search bar
2. "Add Recipe" card disappears; type query
3. Fuzzy text search across all recipe fields
4. Results show recipe name + contextual metadata from matching sections (Ingredients, Preparation, etc.)
5. Tap a result → **Recipe Detail**

## Status

Not started.

## Proposed

## Deferred

### Deferred Screens

- **Household Switcher** — Multi-household deferred; MVP: single household
- **Trash** — Soft delete deferred; MVP: hard delete
- **Pantry Section** — Pantry deferred from MVP
- **Shopping List Section** — Merged into Shopping Tab for MVP (separate section view deferred)
- **Conflict Banner** — Conflict resolution UI deferred; MVP: LWW
- **Conflict Resolution** — Conflict resolution UI deferred; MVP: LWW

### Deferred Screen Features

- **Screen 2 (Manage Household)**: Revoke link (deferred), Change Role action (MVP: Owner + Member only)
- **Screen 3 (Member Detail)**: Change Role option (deferred)
- **Screen 4 (Settings)**: Data export (JSONL) and account deletion — deferred from MVP
- **Screen 6 (Recipe Detail)**: 5-star rating (avg + count), conflict banner — deferred from MVP
- **Screen 8 (Meal Plan Timeline)**: Archive status icons on meal cards — deferred from MVP
- **Screen 9 (Meal Item)**: Archive/Restore action — deferred from MVP

### Deferred Flows

- **Manage Pantry** — Configure staple items excluded from shopping lists; pantry deferred from MVP
- **Switch Households** — View a different household's data; multi-household deferred from MVP
- **Resolve Sync Conflict** — Side-by-side "mine vs theirs" picker; conflict resolution UI deferred (MVP: LWW)
- **Rate a Recipe** — Record 1–5 star rating; ratings deferred from MVP
- **Restore Deleted Recipe** — Recover from trash; soft delete deferred (MVP: hard delete)
- **Meal Plan Entry archiving** — Archive meals to remove ingredients from shopping list; no data model field exists yet; deferred from MVP

### Other Deferred Items

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
- **History Browser**: history.md defers variety tracking, active suggestions, and pattern discovery; no screen defined. Needed for Stage 10.

## Open Questions

- **Calendar integration surface**: In-app calendar view, or only push events to Google Calendar? Where does the user trigger sync?
- **AI Suggest placement**: Where does the "AI Suggest" button live on the Meal Plan Timeline?
- **Meal Plan history**: How does the user browse past meal plans? Scroll up in the timeline, or a separate history view?
- **Default launch tab**: Should returning users land on last-used tab, Meal Plan (most actionable), or Recipes?

## Resolved Questions

- **Shopping list lifecycle**: shopping list is computed fresh from meal plan entries. Deleting a meal plan entry removes its ingredients from the list (MVP: hard delete; archiving deferred).
- **Generate Shopping List trigger**: shopping list is auto-computed when the Shopping tab is opened; no explicit trigger needed.
