# Project Knowledge

Shared project-specific knowledge for all AI agents. This supplements the workflow rules in `AGENTS.md` with technical context about the project.

## App Description

**Kitchen Sync** is a meal planning app for households — from recipe selection to grocery shopping.

- **Offline-first**: Full functionality without internet, syncs when connected
- **Shared household model**: Multiple users collaborate on meal plans
- **Identity**: Google sign-in for authentication
- **Domain**: Recipes, meal plans, grocery lists, household management

## Platform & Tech Stack

- **Platform:** Android + iOS via Kotlin Multiplatform (KMP)
- **Android UI:** Jetpack Compose
- **iOS UI:** SwiftUI
- **Backend:** Firebase/Firestore (managed, no custom server code)
- **Auth:** Firebase Authentication with Google sign-in
- **Local DB:** Room (SQLite) — Room 2.7+ for KMP support
- **DI:** kotlin-inject (compile-time, KMP-native, no reflection)
- **CI:** GitHub Actions (build, test, lint, format on every PR)

## Architecture

**Clean Architecture layers** (inner to outer):

```
domain  →  usecase  →  data  →  presentation
```

- **`domain`**: Pure Kotlin models and interfaces. No platform dependencies.
- **`usecase`**: Business logic orchestration. Single-purpose classes. Depends only on `domain`.
- **`data`**: Repository implementations, Room DAOs, Firestore data sources. Implements `domain` interfaces.
- **`presentation`**: UI layer (Compose/SwiftUI). ViewModels consume use cases.

Dependency rule: inner layers never depend on outer layers.

### Error Handling

`Result<D, E>` sealed type for all fallible operations. Never throw exceptions (except `CancellationException`). Callers use exhaustive `when` expressions. Boundary code wraps external exceptions into `Result.Failure`.

### Sync Model

**MVP: Last-write-wins.** Concurrent edits are resolved by accepting the latest write. The revision model (`basedOnRevision` on RecipeRevision) is retained as the target architecture for future conflict detection and resolution UI, but these are deferred from MVP.

### Dependency Injection

kotlin-inject with KSP code generation. Compile-time verification of the dependency graph. No runtime reflection.

## First Milestone

**Recipes + Meal Plan** — the core loop:
1. Enter and manage recipes
2. Plan meals for the week

## Product Requirements

Full product requirements are documented in `docs/requirements/`. Always consult these when implementing features.

Architecture decisions are documented as ADRs in `docs/architecture/`.

## AI Integration

- **Architecture**: AiEngine interface in domain, implementations in `:ai` module (Gemini cloud + on-device ML Kit). See ADR-009.
- **Action model**: Prepare / Review / Execute — AI proposes actions, user reviews, execution via existing UseCases. See ADR-010.
- **Tool pattern**: Domain capabilities exposed as read-only tools via ToolHandler interface. AI never writes data directly.
- **Reference implementation**: Battery Butler at `/tmp/battery-butler/` — adapted patterns (AiEngine, ToolHandler, tool definitions)

## Documentation Principles

- **Self-improving project**: All agents are obligated to improve documentation on sight. Fix issues when you can; leave a NOTE when you can't.
- **Goals vs Status**: Requirements files always distinguish aspiration (Goal) from reality (Status). This makes it clear whether missing behavior is a bug or simply not-yet-implemented.
- **Notes are cheap**: When in doubt, leave a `> **NOTE (YYYY-MM-DD):** description` block rather than lose an observation. Remove the NOTE after resolving the issue.

## Open Questions

- Calendar integration details depend on Google Calendar API capabilities

## Resolved Questions (2026-03-15)

- **Household model** → **MVP**: single household per user; default unnamed household on first launch; name required when sharing. Multi-household deferred.
- **Roles** → **MVP**: single Owner + Members; Owner generates invite links. Full Admin/Member role system deferred.
- **Invite links** → **MVP**: simple shareable links (no expiry or revocation). Expiry/revocation deferred.
- **Leaving household** → data stays
- **Recipe Markdown template** → restricted Markdown (headers, bullets, bold); sections: Title, Intro, Ingredients, Preparation, Cooking
- **Recipe ownership** → any household member can edit any recipe
- **Ingredient units** → typed sealed class (metric + imperial) plus freeform text variant (no math on freeform)
- **Servings & scaling** → freeform text for serving size; no automatic scaling
- **Related recipes** → bidirectional links between any two recipes
- **Meal slot** → single recipe per slot; no multi-recipe meals
- **Recurring meals** → deferred; not needed for first release
- **Meal plan duration** → default one week
- **Meal plan approval** → not required; any member can publish
- **Shopping list count** → one active list per household
- **Manual shopping items** → yes, users can add items not from recipes
- **Ingredient merging** → no merging; each line stays separate
- **Grocery categories** → built-in DB of common ingredients; optional (uncategorized OK)
- **Real-time sync** → yes, via Firestore listeners
- **Conflict granularity** → per-field
- **Trash visibility** → deferred; hard delete only for MVP. Target: all household members can see and restore
- **Data export** → JSONL format
- **Push notifications** → none
- **Dark mode** → supported from day one
- **Onboarding** → add recipes → plan meals; default unnamed household created automatically
- **Recipe search** → full search + filters (name, ingredients, rating, last cooked) (MVP: name + ingredients only)
- **Calendar event detail** → recipe name as title only
- **Pantry scope** → deferred entirely from MVP. Target: shared per household (one list, all members edit)
- **Recipe revision model** → replaces snapshots; every edit creates immutable RecipeRevision with sequence number; meal plans reference recipeId + revision; all revisions kept permanently; conflict detection via basedOnRevision
- **Conflict resolution UI** → deferred; last-write-wins for MVP. Target: pick one version (mine vs theirs), no manual merge
- **Testing strategy** → full pyramid (unit + integration + E2E)
- **Scale target** → small household (~2-6 members, ~200 recipes)
- **Shopping checkout UX** → checked items gray out in place (strikethrough, can uncheck)
- **Shopping export format** → grouped by grocery category
- **Deletion model** → **MVP**: hard delete only. Target: soft delete with 30-day trash
- **Privacy** → account deletion + full data export (JSONL)
- **AI integration** → Gemini (cloud) + on-device ML Kit; AiEngine interface in domain (ADR-009)
- **AI action model** → Prepare / Review / Execute; all AI outputs require user review (ADR-010)
- **AI recipe formatting** → paste freeform text → AI reformats → user reviews → saves
- **AI meal planning** → AI proposes weekly plan from recipes/history/ratings → user reviews → applies

## Task Management

### `bd` (Beads CLI)

Use `bd` for all task/issue management. **Never modify `.beads/issues.jsonl` directly.** Run `bd help` for full command list.

```bash
# Quick reference
bd init              # Initialize in a git repo
bd create "task"     # Create a task
bd list              # List tasks
bd show <id>         # Show task details
bd close <id>        # Mark task complete
bd help              # See all commands

# Session workflow
bd ready             # Show tasks ready to work on (no blockers)
bd list              # List all open issues
bd create "Title" --type task --priority P2  # Create a task
bd close <id> --reason "Fixed in PR #123"    # Mark complete
bd search "login"    # Search by text
```

### Committing Beads Changes

Beads files should be committed to git like any other code:

```bash
# Include with code changes (recommended)
git add src/... .beads/issues.jsonl
git commit -m "feat: Add feature X (closes bb-123)"

# Standalone beads update (when no code changes)
git add .beads/
git commit -m "chore(beads): Update task tracking"
```

## File Index

| File | Contains | Read when |
|------|----------|-----------|
| `project.md` (this file) | Project knowledge, domain, task management | Always (entry point) |
| `AGENTS.md` | Safety rules, git workflow, core processes | Always (entry point) |
| `.claude/skills/` | Slash command skills (`/dump-context`, `/create-pr`) | Specific operations |
