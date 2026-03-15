# Project Knowledge

Shared project-specific knowledge for all AI agents. This supplements the workflow rules in `AGENTS.md` with technical context about the project.

## App Description

**Kitchen Sync** is a meal planning app for households — from recipe selection to grocery shopping.

- **Offline-first**: Full functionality without internet, syncs when connected
- **Shared household model**: Multiple users collaborate on meal plans
- **Identity**: Google sign-in for authentication
- **Domain**: Recipes, meal plans, grocery lists, household management

## Platform & Tech Stack

**Status: Undecided.** An Android `.gitignore` exists but no code has been written yet. The README notes that platform and tech stack are "still open."

## Product Requirements

Full product requirements are documented in `docs/requirements/`. Always consult these when implementing features.

## Open Questions

From the README (these may evolve as development begins):
- Platform and tech stack still open (Android .gitignore exists but no code yet)
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
| `workflows/` | Step-by-step playbooks for common tasks | Specific operations |
