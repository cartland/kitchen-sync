# Kitchen Sync — Product Requirements

A meal planning app for households — from recipe selection to grocery shopping. Offline-first: full functionality without internet, syncs when connected. Shared household model with Google sign-in for identity.

## Feature Areas

| Feature | Description | Link |
|---------|-------------|------|
| Users & Identity | Google sign-in, household model, per-member ratings | [identity.md](identity.md) |
| Recipe Storage | Structured Markdown recipes, snapshots, manual entry | [recipes.md](recipes.md) |
| Meal Planning | Flexible weekly scheduling, recipe selection, ratings | [meal-planning.md](meal-planning.md) |
| Shopping List | Consolidated lists, smart merging, pantry, checkout flow | [shopping.md](shopping.md) |
| Calendar Integration | Google Calendar events for meal plans | [calendar.md](calendar.md) |
| Meal Plan History | Snapshots, variety tracking, active suggestions | [history.md](history.md) |
| Offline & Sync | Full offline functionality, cloud sync, conflict handling | [sync.md](sync.md) |
| AI Features | LLM-powered workflows with Prepare / Review / Execute model | [ai-features.md](ai-features.md) |

Each feature file uses this section structure:

- **Goal** — The aspirational target: what this feature should do when fully complete (approved design decisions).
- **Status** — What is actually implemented today. Updated as implementation progresses.
- **Proposed** — Ideas under consideration but not yet committed.
- **Deferred** — Decided to do later, not in the current scope.
- **Not Doing** — Explicitly rejected, with rationale.
- **Open Questions** — Unresolved decisions that need input.

## Cross-cutting Decisions

See [decisions.md](decisions.md) for decisions that span multiple features.
