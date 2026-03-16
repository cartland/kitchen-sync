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
| Sync: last-write-wins for MVP | MVP uses LWW; revision model retained for future conflict detection + resolution UI | 2026-03-15 |
| Module structure: Clean Architecture | domain → usecase → data → presentation layers with strict dependency rule | 2026-03-15 |
| DI: kotlin-inject | Compile-time, KMP-native, type-safe, no reflection | 2026-03-15 |
| Error handling: Result\<D, E\> sealed type | Never throw (except CancellationException), exhaustive when expressions | 2026-03-15 |
| CI: GitHub Actions | Build, test, lint, format check on every PR from day one | 2026-03-15 |
| First milestone: Recipes + meal plan | Core loop: enter recipes, plan meals for the week | 2026-03-15 |
| ~~Household model: single per user~~ | ~~One household membership at a time~~ — **Superseded** by "Multiple households per user" below | 2026-03-15 |
| Recipe search: full search (filters deferred) | Search by name/ingredients; rating and last-cooked filters deferred with ratings | 2026-03-15 |
| Conflict resolution UI: deferred | Target: show both versions, user picks "mine" or "theirs"; deferred — MVP uses last-write-wins | 2026-03-15 |
| Testing strategy: full pyramid | Unit + integration + E2E tests | 2026-03-15 |
| Scale target: small household | ~2-6 members, ~200 recipes; optimize for simplicity over scale | 2026-03-15 |
| Deletion model: hard delete for MVP | MVP uses hard delete; soft delete with 30-day trash deferred | 2026-03-15 |
| Privacy: account deletion + data export | Full data export (recipes, plans as JSON/files); account deletion available | 2026-03-15 |
| Shopping checkout UX: gray out in place | Checked items get strikethrough, remain visible, can be unchecked | 2026-03-15 |
| Shopping export: grouped by category | Copy export grouped by grocery category (produce, dairy, meat, etc.) | 2026-03-15 |
| Pantry: deferred | Target: shared per household (one staples list, all members edit); deferred from MVP | 2026-03-15 |
| Calendar event detail: recipe name only | Event title is recipe name; no description or ingredients in the event | 2026-03-15 |
| Household: single per user for MVP | MVP: one household per user; multi-household with switcher deferred | 2026-03-15 |
| Roles: Owner + Member for MVP | MVP: single owner generates invite links; members edit shared data. Full admin/member system deferred | 2026-03-15 |
| Invite links: simple for MVP | MVP: shareable links with no expiry or revocation. Expiry/revocation deferred | 2026-03-15 |
| Last Admin auto-promotion: deferred | Target: auto-promote on last admin departure; deferred with full role system | 2026-03-15 |
| Leaving household: data stays | Recipes/plans belong to household; rating removal deferred with ratings | 2026-03-15 |
| Recipe Markdown: restricted format | Headers, bullets, bold only; sections: Title, Intro, Ingredients, Preparation, Cooking | 2026-03-15 |
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
| Conflict granularity: per-field (deferred) | Target: per-field conflict detection; deferred with conflict resolution UI | 2026-03-15 |
| Trash: deferred | Target: all household members can see and restore trashed items; deferred — hard delete for MVP | 2026-03-15 |
| Data export format: JSONL | Full data export as JSONL | 2026-03-15 |
| No push notifications | No notifications for meal plans, shopping lists, or member changes | 2026-03-15 |
| Dark mode from day one | Support dark mode in initial release | 2026-03-15 |
| Onboarding: add recipes → plan meals | Default unnamed household created on first launch; name required when sharing | 2026-03-15 |
| AI integration: Gemini + on-device ML Kit | Cloud engine for quality, on-device for offline/privacy; AiEngine interface in domain (ADR-009) | 2026-03-15 |
| AI action model: Prepare / Review / Execute | All AI outputs require user review before saving; AI never writes data directly (ADR-010) | 2026-03-15 |
| AI tools are read-only | AI can query recipes, history, ratings (ratings deferred from MVP) via ToolHandler but has no write access | 2026-03-15 |
| AI-assisted recipe formatting | Paste freeform text → AI reformats to restricted Markdown → user reviews → approves | 2026-03-15 |
| AI-assisted meal plan generation | AI proposes weekly plan from recipes/history/ratings (ratings deferred from MVP) → user reviews/edits → approves | 2026-03-15 |
| Recipe revision model replaces snapshots | Every edit creates an immutable RecipeRevision with sequence number; meal plans reference recipeId + revision; no separate snapshot entity; all revisions kept permanently | 2026-03-15 |
| Recipe conflict detection via basedOnRevision | Client sends basedOnRevision when editing; server accepts if it matches currentRevision, otherwise last-write-wins for MVP (conflict resolution UI deferred) | 2026-03-15 |
| Staged project plan with incremental planning | Plan additional planning — don't detail later stages upfront; expand each stage when earlier stages complete | 2026-03-15 |
| Documentation self-improvement obligation | Agents must fix doc issues on sight or leave a NOTE block; observations must not be lost between sessions | 2026-03-15 |
| Goals vs Status in requirements files | Renamed "Committed" to "Goal" (aspiration) and added "Status" section (reality) to every requirements file; makes it clear whether missing behavior is a bug or not-yet-implemented | 2026-03-15 |
| User flows document: single cross-cutting file | Flows cross feature boundaries (onboarding touches identity, recipes, meal planning); a single file is better than per-feature flow docs | 2026-03-15 |
| **REVISED**: 5-star ratings replace like/dislike | Per-person 1–5 star ratings with household average and count; replaces binary like/dislike model | 2026-03-15 |
| **REVISED**: Recipe sections include Intro | Sections: Title, Intro, Ingredients, Preparation, Cooking; Intro added for recipe description/notes between title and ingredients | 2026-03-15 |
| No cook time, prep time, or serving size on Recipe | Not needed for MVP; search filters updated to remove cook time | 2026-03-15 |
| Data model documented in data-model.md | Single file for all entity definitions, fields, and relationships | 2026-03-15 |
