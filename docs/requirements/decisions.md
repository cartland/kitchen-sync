# Cross-cutting Decision Log

Decisions that span multiple features or don't belong to a single feature area.

| Decision | Rationale | Date |
|----------|-----------|------|
| Google sign-in over other auth | Aligns with Google Calendar integration, which is a core feature | 2026-03-14 |
| No URL import for recipes | Manual entry only for now; keeps scope focused | 2026-03-14 |
| No grocery ordering automation | Human places orders manually; avoids integration complexity | 2026-03-14 |
| No recipe tags/categories | Flat list is sufficient for now; can revisit as recipe count grows | 2026-03-14 |
| Platform: KMP (Android + iOS) | Shared business logic in Kotlin, native UI per platform (Compose/SwiftUI) | 2026-03-15 |
| Backend: Firebase/Firestore | Managed backend, built-in auth, real-time sync, aligns with Google sign-in | 2026-03-15 |
| Local DB: Room (SQLite) | Mature, KMP support via Room 2.7+, full SQL for complex queries | 2026-03-15 |
| Sync: Conflict detection + user resolution | Per requirements: detect conflicts, show error, let user resolve. No silent LWW | 2026-03-15 |
| Module structure: Clean Architecture | domain → usecase → data → presentation layers with strict dependency rule | 2026-03-15 |
| DI: kotlin-inject | Compile-time, KMP-native, type-safe, no reflection | 2026-03-15 |
| Error handling: Result\<D, E\> sealed type | Never throw (except CancellationException), exhaustive when expressions | 2026-03-15 |
| CI: GitHub Actions | Build, test, lint, format check on every PR from day one | 2026-03-15 |
| First milestone: Recipes + meal plan | Core loop: enter recipes, plan meals for the week | 2026-03-15 |
| Household model: single per user | One household membership at a time; invite link to join | 2026-03-15 |
| Recipe search: full search + filters | Search by name/ingredients, filter by cook time, rating, last cooked | 2026-03-15 |
| Conflict resolution UI: pick one version | Show both versions, user picks "mine" or "theirs"; no manual merge | 2026-03-15 |
| Testing strategy: full pyramid | Unit + integration + E2E tests | 2026-03-15 |
| Scale target: small household | ~2-6 members, ~200 recipes; optimize for simplicity over scale | 2026-03-15 |
| Deletion model: soft delete | 30-day trash before permanent removal | 2026-03-15 |
| Privacy: account deletion + data export | Full data export (recipes, plans as JSON/files); account deletion available | 2026-03-15 |
| Shopping checkout UX: gray out in place | Checked items get strikethrough, remain visible, can be unchecked | 2026-03-15 |
| Shopping export: grouped by category | Copy export grouped by grocery category (produce, dairy, meat, etc.) | 2026-03-15 |
| Pantry scope: shared per household | One staples list per household, all members can view and edit | 2026-03-15 |
| Calendar event detail: recipe name only | Event title is recipe name; no description or ingredients in the event | 2026-03-15 |
| **REVISED**: Multiple households per user | Users can join multiple households with an active household switcher; replaces single-household model | 2026-03-15 |
| Roles: Admin and Member | Admins can invite/remove/delete; Members can edit all shared data; at least one Admin required | 2026-03-15 |
| Invite links: multi-use, 1-day expiry, revocable | Admins generate links; multiple people can join before expiry | 2026-03-15 |
| Last Admin auto-promotion | If last Admin leaves, remaining Members become Admin; if none remain, household deleted | 2026-03-15 |
| Leaving household: data stays, ratings removed | Recipes/plans belong to household; departing member's ratings are deleted | 2026-03-15 |
| Recipe Markdown: restricted format | Headers, bullets, bold only; sections: Title, Ingredients, Preparation, Cooking | 2026-03-15 |
| Any member can edit any recipe | No per-recipe ownership; all household members have equal edit access | 2026-03-15 |
| Ingredient units: typed + freeform | Sealed class with metric/imperial units plus freeform text (no math on freeform) | 2026-03-15 |
| No ingredient scaling | Serving size is freeform text; no automatic scaling of ingredients | 2026-03-15 |
| Single recipe per meal slot | No multi-recipe meals; use "related recipes" for bidirectional linking | 2026-03-15 |
| No recurring meals | Deferred; not needed for first release | 2026-03-15 |
| Default plan duration: one week | Meal plans default to a week | 2026-03-15 |
| No meal plan approval required | Any household member can publish a meal plan | 2026-03-15 |
| One shopping list per household | No multiple lists (e.g., per store) | 2026-03-15 |
| Manual shopping list items | Users can add items not from recipes | 2026-03-15 |
| No ingredient merging | Each ingredient line stays separate on the shopping list | 2026-03-15 |
| Grocery categories: optional built-in DB | Common ingredients mapped to categories; uncategorized items allowed | 2026-03-15 |
| Real-time sync via Firestore listeners | Changes appear instantly when online | 2026-03-15 |
| Conflict granularity: per-field | Conflicts detected and resolved at the field level | 2026-03-15 |
| Trash visible to all household members | Any member can see and restore trashed items | 2026-03-15 |
| Data export format: JSONL | Full data export as JSONL | 2026-03-15 |
| No push notifications | No notifications for meal plans, shopping lists, or member changes | 2026-03-15 |
| Dark mode from day one | Support dark mode in initial release | 2026-03-15 |
| Onboarding: add recipes → plan meals | Default unnamed household created on first launch; name required when sharing | 2026-03-15 |
| AI integration: Gemini + on-device ML Kit | Cloud engine for quality, on-device for offline/privacy; AiEngine interface in domain (ADR-009) | 2026-03-15 |
| AI action model: Prepare / Review / Execute | All AI outputs require user review before saving; AI never writes data directly (ADR-010) | 2026-03-15 |
| AI tools are read-only | AI can query recipes, history, ratings via ToolHandler but has no write access | 2026-03-15 |
| AI-assisted recipe formatting | Paste freeform text → AI reformats to restricted Markdown → user reviews → approves | 2026-03-15 |
| AI-assisted meal plan generation | AI proposes weekly plan from recipes/history/ratings → user reviews/edits → approves | 2026-03-15 |
| Documentation self-improvement obligation | Agents must fix doc issues on sight or leave a NOTE block; observations must not be lost between sessions | 2026-03-15 |
| Goals vs Status in requirements files | Renamed "Committed" to "Goal" (aspiration) and added "Status" section (reality) to every requirements file; makes it clear whether missing behavior is a bug or not-yet-implemented | 2026-03-15 |
