# User Flows & Screens

Map of the app: every screen, how they connect, and the key user journeys. This file bridges the per-feature requirements files by showing how features compose into end-to-end experiences.

## Goal

### Screen Inventory

#### Authentication & Household

| # | Screen | Purpose |
|---|--------|---------|
| 1 | Sign-In | Google sign-in entry point |
| 2 | Household Switcher | Select active household |
| 3 | Household Settings | Members, roles, invite links, leave/delete |
| 4 | Invite Link Management | Generate/revoke invite links (Admin only) |

#### Recipes

| # | Screen | Purpose |
|---|--------|---------|
| 5 | Recipe List | Searchable/filterable list of household recipes |
| 6 | Recipe Detail | Full recipe view (rendered Markdown) |
| 7 | Recipe Editor | Create/edit recipe with Markdown template |
| 8 | AI Recipe Formatter | Paste freeform text, review AI output, approve |
| 9 | Recipe Diff View | Compare snapshot to current version |

#### Meal Planning

| # | Screen | Purpose |
|---|--------|---------|
| 10 | Meal Plan View | Weekly grid of meal slots |
| 11 | Recipe Picker | Select recipe for a meal slot |
| 12 | AI Meal Plan Generator | Review AI-proposed weekly plan, approve |
| 13 | Meal Preferences | Configure default days/times/meals per day |

#### Shopping

| # | Screen | Purpose |
|---|--------|---------|
| 14 | Shopping List | Active list with checkboxes, categories, source citations |
| 15 | Pantry / Staples | Manage persistent staples list |
| 16 | Shopping Export | Copy remaining items grouped by category |

#### History & Ratings

| # | Screen | Purpose |
|---|--------|---------|
| 17 | Meal History | Browse past plans with recipe snapshots |
| 18 | Ratings | Per-person like/dislike with household aggregate |

#### System

| # | Screen | Purpose |
|---|--------|---------|
| 19 | Trash | View and restore soft-deleted items (30-day retention) |
| 20 | Conflict Resolution | Side-by-side "mine vs theirs" picker |
| 21 | Account Settings | Dark mode, data export (JSONL), account deletion |

### Core User Flows

#### 1. First Launch / Onboarding

> User goal: Get started with the app and set up their first meal plan.

1. **Sign-In** — User signs in with Google
2. Default household created automatically (unnamed)
3. **Recipe List** — Empty state prompts user to add first recipe
4. **Recipe Editor** — User creates their first recipe
5. **Recipe Detail** — Recipe saved, user sees the result
6. **Meal Plan View** — User plans their first meal

#### 2. Add Recipe (Manual)

> User goal: Add a new recipe to the household collection.

1. **Recipe List** — Tap "Add Recipe"
2. **Recipe Editor** — Fill in Markdown template (title, ingredients, preparation, cooking)
3. Save
4. **Recipe Detail** — View the new recipe

#### 3. Add Recipe (AI-Assisted)

> User goal: Quickly add a recipe from unstructured text (e.g., copied from a website or message).

1. **Recipe List** — Tap "Add Recipe" → choose "Paste Text"
2. **AI Recipe Formatter** — Paste freeform text
3. AI reformats to restricted Markdown (Prepare phase)
4. User reviews the formatted output (Review phase)
5. User approves → recipe saved (Execute phase)
6. **Recipe Detail** — View the new recipe

#### 4. Plan Meals for the Week

> User goal: Fill out the weekly meal plan with recipes.

1. **Meal Plan View** — See weekly grid with empty/filled slots
2. Tap an empty slot
3. **Recipe Picker** — Search/filter recipes, select one
4. Return to **Meal Plan View** — Slot filled
5. Repeat for remaining slots

#### 5. AI-Assisted Meal Planning

> User goal: Get a complete weekly plan suggestion based on recipes, history, and ratings.

1. **Meal Plan View** — Tap "AI Suggest"
2. **AI Meal Plan Generator** — AI proposes a weekly plan (Prepare phase)
3. User reviews and edits the proposal (Review phase)
4. User approves → plan applied (Execute phase)
5. **Meal Plan View** — Slots filled with approved selections

#### 6. Generate Shopping List

> User goal: Create a shopping list from the current meal plan.

1. **Meal Plan View** — Tap "Generate Shopping List"
2. System consolidates ingredients from planned recipes
3. Pantry/staples items automatically excluded
4. **Shopping List** — Review generated list (items show source recipe)

#### 7. Shopping Trip

> User goal: Check off items while shopping and share remaining items.

1. **Shopping List** — View items grouped by grocery category
2. Check off items as purchased (strikethrough, remain visible, can uncheck)
3. Optionally tap "Export"
4. **Shopping Export** — Copy remaining unchecked items grouped by category

#### 8. Invite Household Member

> User goal: Add someone to the household.

1. **Household Settings** — Tap "Invite"
2. **Invite Link Management** — Generate invite link (multi-use, 1-day expiry)
3. Share link externally (message, email, etc.)
4. Recipient opens link → **Sign-In** (if needed) → joins household

#### 9. Switch Households

> User goal: View a different household's data.

1. **Household Switcher** — See list of joined households
2. Select a household
3. App context changes — all screens now show selected household's data

#### 10. Resolve Sync Conflict

> User goal: Resolve a conflicting edit made by another household member.

1. User is editing (e.g., recipe, meal plan)
2. Conflict detected on sync (base version differs from server)
3. **Conflict Resolution** — Side-by-side view of "mine" vs "theirs"
4. User picks one version
5. Return to previous screen with resolved data

#### 11. Rate a Meal

> User goal: Record whether they liked or disliked a meal.

1. **Recipe Detail** (or after a planned meal) — Tap like/dislike
2. Rating saved for this user
3. Household aggregate updates (e.g., "2 of 3 liked it")

#### 12. Restore Deleted Item

> User goal: Recover something that was accidentally deleted.

1. **Trash** — Browse soft-deleted items (available for 30 days)
2. Select an item
3. Tap "Restore"
4. Item reappears in its original location

## Status

Not started.

## Proposed

## Deferred

## Not Doing

## Open Questions

- **Navigation model**: Bottom tab bar? Which tabs? How many?
- **Home screen**: Dashboard with summary, or open directly to Meal Plan View / Recipe List?
- **Household switcher placement**: Top bar dropdown? Separate screen? How prominent when user has only one household?
- **Rating entry point**: When and where does the user rate a meal? On Recipe Detail only, or also prompted after a planned meal?
- **Shopping list lifecycle**: When is a shopping list considered "done"? Manual clear, or auto-clear when a new list is generated?
- **Calendar integration surface**: In-app calendar view, or only push events to Google Calendar? Where does the user trigger sync?
- **AI feature entry points**: How are AI actions surfaced in the UI? Inline buttons, floating action menu, or dedicated section?
