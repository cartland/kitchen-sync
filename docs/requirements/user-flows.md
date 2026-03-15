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
| 3 | Manage Household | Member list with roles; tap a member to open member detail | Member list with role badges (Admin/Member), Invite button (Admin only) | → Member Detail (tap member); → Invite Link Display (tap Invite) |
| 4 | Member Detail | Role-based options: Admins see remove/change role; Members see leave | Role badge; Admin sees: Change Role, Remove Member; Member sees: Leave Household | → Manage Household (back); confirmation dialogs for destructive actions |
| 5 | Settings | Dark mode (default: match system), data export (JSONL), account deletion | Dark mode toggle (default: system), Data Export button (JSONL), Delete Account button | → Export confirmation; → Delete Account confirmation dialog |

#### Recipes (Tab 1)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 6 | Recipe List | Search bar at top; "Add Recipe" card as first item (hidden during search); recipe cards below | Search bar, "Add Recipe" card (first item, hidden during search), recipe cards (title + metadata), filter options (rating, last cooked) | → Recipe Detail (tap card); → Recipe Editor (tap Add Recipe); → Trash (via search results) |
| 7 | Recipe Detail | Full-screen rendered view; title row with 5-star rating (average + count) on top-right; edit button; conflict banner when applicable | Title, 5-star rating (avg + count, tappable), sections (Intro, Ingredients, Preparation, Cooking) in rendered Markdown, Edit button, Related Recipes links, Conflict banner (conditional) | → Recipe Editor (tap Edit); → Conflict Resolution (tap Resolve on banner); → related Recipe Detail (tap link) |
| 8 | Recipe Editor | Section placeholders (Title, Intro, Ingredients, Preparation, Cooking) styled in gray; rich text fields in edit mode with formatting toolbar (bullets, bold); smart paste; AI restructure button | Section fields with gray placeholders (Title, Intro, Ingredients, Preparation, Cooking), formatting toolbar (bullets, bold), AI Restructure button, Save/Cancel | → Recipe Detail (Save); → Recipe List (Cancel); → AI review preview (tap AI Restructure) |
| 9 | Trash | Accessed via search results; recipe cards in distinct colors; restore option; 30-day retention | Deleted recipe cards (distinct color), Restore button per card, 30-day retention indicator | → Recipe List (Restore); → Recipe Detail (tap card) |

#### Meal Planning (Tab 2)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 10 | Meal Plan Timeline | Infinite scrolling timeline anchored to today (future below, past above); day dividers with day-of-week + date; "+" button per day; meals listed under their day | Day dividers (day-of-week + date), "+" button per day, meal cards under each day, today anchor, Generate Shopping List action | → Recipe Picker (tap "+"); → Meal Item expand (tap meal); → Shopping Tab (Generate Shopping List) |
| 11 | Meal Item (expanded) | Inline accordion (one at a time) on a meal in the timeline; options: View, Date, Remove | Inline accordion with: View button, Date button, Remove button | → Recipe Detail (View); → Date Picker (Date); → Remove Undo Placeholder (Remove) |
| 12 | Recipe Picker | Bottom sheet with search bar on top; AI-powered recipe suggestions below | Bottom sheet, search bar, recipe list, AI suggestions section | → Timeline with new meal (select recipe); → dismiss (swipe down / tap outside) |
| 13 | Date Picker | Date picker opened from expanded meal item's "Date" option | Calendar/date selector, confirm/cancel | → Timeline (confirm, meal rescheduled); → Meal Item (cancel) |
| 14 | Remove Undo Placeholder | Temporary placeholder in removed meal's slot with Undo option | Placeholder card in removed meal's slot, Undo button, auto-dismiss timer | → Timeline restored (Undo); → Timeline without meal (timeout/dismiss) |

#### Shopping (Tab 3)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 15 | Shopping Tab | Two collapsible sections with distinct background colors: Pantry header and Shopping List header | Two collapsible section headers (Pantry, Shopping List) with distinct backgrounds | → Pantry Section (expand); → Shopping List Section (expand) |
| 16 | Pantry Section | Collapsible list of staple items; "+" next to each item to manually add to shopping list | Staple item list, "+" button per item (add to shopping list), add new item field | → Shopping List (item added via "+") |
| 17 | Shopping List Section | Auto-populated from meal plan; checkbox on left; meal date badge on right (date like "Sep 30" or count like "2" for multiple meals); tappable badge expands recipe/date detail with links to Meal Plan; "+" at bottom to manually add items; checked items turn gray with strikethrough | Checklist items (checkbox left, name+quantity center, meal date badge right), "+" at bottom for manual items, checked items: gray strikethrough (remain visible, uncheckable), Export button | → Meal Plan day (tap badge → expand source detail → tap link); → Shopping Export (tap Export) |
| 18 | Shopping Export | Copy remaining unchecked items grouped by grocery category | Copy action: remaining unchecked items grouped by grocery category | → Shopping List (back/dismiss) |

#### System (overlay / contextual)

| # | Screen | Purpose | Key Elements | Navigates To |
|---|--------|---------|--------------|--------------|
| 19 | Conflict Banner | Top banner on Recipe Detail when conflict detected; warning icon appears on recipe references everywhere; blocks editing until resolved | Top banner on Recipe Detail, warning icon, explanation text, Resolve button; warning icon also shown on recipe references elsewhere | → Conflict Resolution (tap Resolve) |
| 20 | Conflict Resolution | Side-by-side "mine vs theirs" picker; opened from conflict banner's Resolve button | Side-by-side "mine" vs "theirs" display, pick-one selection (no manual merge) | → Recipe Detail (selection made, conflict resolved) |
| 21 | Profile Menu | Dropdown from top-right profile icon: Switch Household, Manage Household, Settings, Sign Out | Dropdown: Switch Household, Manage Household, Settings, Sign Out | → Household Switcher; → Manage Household; → Settings; → Sign-In (Sign Out) |
| 22 | Invite Link Display | Show generated invite link for sharing | Invite link text, Copy button, share action, expiry indicator (1-day), Revoke button (Admin) | → Manage Household (back/done); external share sheet (Share) |

### Role-Based UI Elements

Certain UI controls are only visible to household Admins:

- **Manage Household**: Invite button, Remove Member action, Change Role action
- **Invite Link Display**: Revoke button
- **Member Detail**: Remove Member option (Admin viewing another member)

Members see Leave Household instead of Remove on their own Member Detail screen. If the last Admin leaves, remaining Members are auto-promoted to Admin.

### Empty States

- **Recipe List**: "Add Recipe" card (always first item) followed by gray ghost/placeholder cards in the same shape as recipe cards, implying what the list will look like
- **Meal Plan Timeline**: Days shown with "+" buttons; minimum height per day; no ghost cards needed since the timeline structure is self-explanatory
- **Shopping List**: Empty ruled lines matching the dividers between list items; Pantry and Shopping List headers still visible

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
5. Optionally tap AI Restructure button to reformat into proper sections (Prepare → Review → Execute): AI reformats pasted text into restricted Markdown sections; a preview pane shows the reformatted content before the user approves and saves
6. Save → **Recipe Detail**

#### 4. Add Meal to Plan

> User goal: Add a recipe to a specific day.

1. **Meal Plan Timeline** — Scroll to desired day
2. Tap "+" on that day
3. **Recipe Picker** (bottom sheet) — Search or browse AI suggestions
4. Select a recipe → meal added to the day
5. Timeline updates with the new meal listed under the day

#### 5. Manage Planned Meal

> User goal: View, reschedule, or remove a planned meal.

1. **Meal Plan Timeline** — Tap a meal
2. **Meal Item** expands inline (accordion, one at a time) showing: View, Date, Remove
3. **View** → opens **Recipe Detail** full-screen
4. **Date** → opens **Date Picker** to reschedule
5. **Remove** → meal removed, **Undo Placeholder** appears in its slot

#### 6. AI-Assisted Meal Planning

> User goal: Get a complete weekly plan suggestion based on recipes, history, and ratings.

1. **Meal Plan Timeline** — Tap "AI Suggest"
2. AI proposes meals for the week (Prepare phase)
3. User reviews and edits the proposal (Review phase)
4. User approves → meals applied to timeline (Execute phase)

#### 7. Generate Shopping List

> User goal: Create a shopping list from the current meal plan.

1. **Meal Plan Timeline** — Tap "Generate Shopping List"
2. System consolidates ingredients from planned recipes
3. Pantry/staples items automatically excluded from the generated list
4. Switch to **Shopping** tab → **Shopping List Section** populated with items
5. Each item shows meal date badge on right linking to source recipe/day; badges display a date (e.g., "Sep 30") or count (e.g., "2") for items used in multiple meals

#### 8. Shopping Trip

> User goal: Check off items while shopping and share remaining items.

1. **Shopping Tab** — Expand Shopping List section
2. Check off items as purchased (gray strikethrough, remain visible, can uncheck)
3. Tap meal date badge on any item → expands recipe name + meal plan dates → tap to jump to that day in Meal Plan
4. Optionally tap "Export" → **Shopping Export** copies remaining items grouped by category

#### 9. Manage Pantry

> User goal: Configure staple items that should be excluded from shopping lists.

1. **Shopping Tab** — Expand Pantry section
2. Add staple items to the pantry
3. Tap "+" next to a pantry item to manually add it to the shopping list
4. Pantry items are automatically excluded when generating shopping lists from meal plans

#### 10. Invite Household Member

> User goal: Add someone to the household.

1. **Profile Menu** → Manage Household
2. **Manage Household** — Tap "Invite" (Admin only)
3. **Invite Link Display** — Generate invite link (multi-use, 1-day expiry); Admin can revoke the link
4. Share link externally via share sheet (message, email, etc.)
5. Recipient opens link → **Sign-In** (if needed) → joins household

#### 11. Switch Households

> User goal: View a different household's data.

1. **Profile Menu** → Switch Household
2. **Household Switcher** dropdown — Lists joined households + "Create New"
3. Select a household → app context changes, all tabs show selected household's data

#### 12. Resolve Sync Conflict

> User goal: Resolve a conflicting edit made by another household member.

1. Conflict detected on sync → editing blocked on affected recipe
2. Warning icon appears next to recipe name wherever it is displayed
3. **Recipe Detail** — Conflict banner at top explains the issue
4. Tap "Resolve" → **Conflict Resolution** screen
5. Side-by-side view of "mine" vs "theirs" with per-field conflict granularity → user picks "Use Mine" or "Use Theirs" (no manual merge)
6. Return to **Recipe Detail** with resolved data, editing re-enabled

#### 13. Rate a Recipe

> User goal: Record a star rating for a recipe.

1. **Recipe Detail** — Tap 5-star rating in top-right of title row
2. Select star rating (1-5)
3. Rating saved for this user; average and count update

#### 14. Restore Deleted Recipe

> User goal: Recover a recipe that was accidentally deleted.

1. **Recipe List** — Search for "Trash" (or see Trash as last result in any search)
2. **Trash** — Browse deleted recipe cards (distinct color)
3. Tap a recipe → Restore
4. Recipe reappears in the Recipe List

#### 15. Search Recipes

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

## Not Doing

## Missing Screens (to define during sub-discovery)

- **Recipe Diff View**: Mentioned in recipes.md for comparing revisions; no screen defined yet. Needed for Stage 3.
- **AI Proposal Review**: ADR-010 Prepare/Review/Execute flow needs a review screen for AI-generated content. Needed for Stage 8.
- **Data Export Flow**: sync.md mentions JSONL export; no flow defined. Needed for Stage 6+.
- ~~**Invite Link Display**: identity.md describes invite generation; no screen for showing/sharing the link. Needed for Stage 7.~~ → Added as Screen 22.
- **History Browser**: history.md references history browsing; no screen defined. Needed for Stage 10.

## Open Questions

- **Shopping list lifecycle**: When is a shopping list considered "done"? Manual clear, or auto-clear when a new list is generated? (Note: partially blocks Stage 4 — must resolve before shipping shopping list generation)
- **Calendar integration surface**: In-app calendar view, or only push events to Google Calendar? Where does the user trigger sync?
- **AI Suggest placement**: Where does the "AI Suggest" button live on the Meal Plan Timeline?
- **Generate Shopping List trigger**: Where does this action live — on the Meal Plan tab, the Shopping tab, or both?
- **Meal Plan history**: How does the user browse past meal plans? Scroll up in the timeline, or a separate history view?
- **Default launch tab**: Should returning users land on last-used tab, Meal Plan (most actionable), or Recipes?
