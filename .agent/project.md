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

Conflict detection with user resolution. Each entity carries a version. On sync, if the base version differs from the server's current version, a conflict is detected and surfaced to the user. No silent last-writer-wins.

### Dependency Injection

kotlin-inject with KSP code generation. Compile-time verification of the dependency graph. No runtime reflection.

## First Milestone

**Recipes + Meal Plan** — the core loop:
1. Enter and manage recipes
2. Plan meals for the week

## Product Requirements

Full product requirements are documented in `docs/requirements/`. Always consult these when implementing features.

Architecture decisions are documented as ADRs in `docs/architecture/`.

## Open Questions

- Recipe Markdown template format (user will provide later)

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
