# User Flows & Screens

Map of the app: every screen, how they connect, and the key user journeys. This file bridges the per-feature requirements files by showing how features compose into end-to-end experiences.

## Goal

### Navigation

- **Bottom tab bar** with three tabs: **Recipes**, **Meal Plan**, **Shopping List**
- **Profile icon** (top-right) opens a dropdown menu: Switch Household, Manage Household, Settings, Sign Out
- Default launch tab: Recipes (first launch) / last-used tab (returning user)

### Screen Inventory

#### Authentication & Household

| # | Screen | Purpose |
|---|--------|---------|
| 1 | Sign-In | Google sign-in entry point |
| 2 | Household Switcher | Dropdown from profile menu; lists joined households + "Create New" option |
| 3 | Manage Household | Member list with roles; tap a member to open member detail |
| 4 | Member Detail | Role-based options: Admins see remove/change role; Members see leave |
| 5 | Settings | Dark mode (default: match system), data export (JSONL), account deletion |

#### Recipes (Tab 1)

| # | Screen | Purpose |
|---|--------|---------|
| 6 | Recipe List | Search bar at top; "Add Recipe" card as first item (hidden during search); recipe cards below |
| 7 | Recipe Detail | Full-screen rendered view; title row with 5-star rating (average + count) on top-right; edit button; conflict banner when applicable |
| 8 | Recipe Editor | Section placeholders (Title, Intro, Ingredients, Preparation, Cooking) styled in gray; rich text fields in edit mode with formatting toolbar (bullets, bold); smart paste; AI restructure button |
| 9 | Trash | Accessed via search results; recipe cards in distinct colors; restore option; 30-day retention |

#### Meal Planning (Tab 2)

| # | Screen | Purpose |
|---|--------|---------|
| 10 | Meal Plan Timeline | Infinite scrolling timeline anchored to today (future below, past above); day dividers with day-of-week + date; "+" button per day; meals listed under their day |
| 11 | Meal Item (expanded) | Inline accordion (one at a time) on a meal in the timeline; options: View, Date, Remove |
| 12 | Recipe Picker | Bottom sheet with search bar on top; AI-powered recipe suggestions below |
| 13 | Date Picker | Date picker opened from expanded meal item's "Date" option |
| 14 | Remove Undo Placeholder | Temporary placeholder in removed meal's slot with Undo option |

#### Shopping (Tab 3)

| # | Screen | Purpose |
|---|--------|---------|
| 15 | Shopping Tab | Two collapsible sections with distinct background colors: Pantry header and Shopping List header |
| 16 | Pantry Section | Collapsible list of staple items; "+" next to each item to manually add to shopping list |
| 17 | Shopping List Section | Auto-populated from meal plan; checkbox on left; meal date badge on right (date like "Sep 30" or count like "2" for multiple meals); tappable badge expands recipe/date detail with links to Meal Plan; "+" at bottom to manually add items; checked items turn gray with strikethrough |
| 18 | Shopping Export | Copy remaining unchecked items grouped by grocery category |

#### System (overlay / contextual)

| # | Screen | Purpose |
|---|--------|---------|
| 19 | Conflict Banner | Top banner on Recipe Detail when conflict detected; warning icon appears on recipe references everywhere; blocks editing until resolved |
| 20 | Conflict Resolution | Side-by-side "mine vs theirs" picker; opened from conflict banner's Resolve button |
| 21 | Profile Menu | Dropdown from top-right profile icon: Switch Household, Manage Household, Settings, Sign Out |

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
5. Optionally tap AI restructure button to reformat into proper sections (Prepare → Review → Execute)
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
3. Pantry/staples items automatically excluded
4. Switch to **Shopping** tab → **Shopping List Section** populated with items
5. Each item shows meal date badge on right linking to source recipe/day

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
2. **Manage Household** — Tap "Invite"
3. Generate invite link (multi-use, 1-day expiry)
4. Share link externally (message, email, etc.)
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
5. Side-by-side view of "mine" vs "theirs" → user picks one version
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
- **Invite Link Display**: identity.md describes invite generation; no screen for showing/sharing the link. Needed for Stage 7.
- **History Browser**: history.md references history browsing; no screen defined. Needed for Stage 10.

## Open Questions

- **Shopping list lifecycle**: When is a shopping list considered "done"? Manual clear, or auto-clear when a new list is generated? (Note: partially blocks Stage 4 — must resolve before shipping shopping list generation)
- **Calendar integration surface**: In-app calendar view, or only push events to Google Calendar? Where does the user trigger sync?
- **AI Suggest placement**: Where does the "AI Suggest" button live on the Meal Plan Timeline?
- **Generate Shopping List trigger**: Where does this action live — on the Meal Plan tab, the Shopping tab, or both?
- **Meal Plan history**: How does the user browse past meal plans? Scroll up in the timeline, or a separate history view?
- **Default launch tab**: Should returning users land on last-used tab, Meal Plan (most actionable), or Recipes?
