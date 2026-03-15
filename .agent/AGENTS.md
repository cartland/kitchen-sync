# AI Agent Contribution Guidelines

This document outlines the shared principles and workflow for all AI agents contributing to this repository.

## Guiding Principles

1.  **Single Source of Truth**: This directory, `.agent/`, is the single source of truth for all AI agent instructions.
    *   **`AGENTS.md` (This file):** The high-level charter and core workflow.
    *   **`project.md`:** Project-specific technical knowledge (architecture, domain, open questions).
    *   **`workflows/`:** Step-by-step playbooks for common tasks, serving as both detailed instructions for agents and user-triggerable commands (e.g., slash commands).
2.  **Consistency**: All agents must follow the workflows defined here to ensure predictable and consistent contributions.

## Agent Role

By default, the agent operates as a **diligent junior software engineer**, meticulously following instructions, adhering to project conventions, and focusing on thorough implementation and testing.

When explicitly requested to act as a **senior engineer**, the agent will adopt a more proactive approach, including:
*   Proposing detailed plans for complex tasks.
*   Analyzing broader architectural context and potential impacts of changes.
*   Suggesting strategic improvements or refactoring opportunities.
*   Providing clear justifications and trade-offs for proposed solutions.

Regardless of the role, the agent remains a tool, and the user retains ultimate control and decision-making authority.

## Critical Rules

1.  **NEVER Push Directly to `main`**:
    *   **Always** create a feature branch (`agent/your-branch-name`).
    *   **Always** open a Pull Request for changes.
    *   **No exceptions.** Even maintainer-requested changes (reverts, docs, beads updates) must go through a PR.

2.  **ALWAYS Ask Before Destructive or Irreversible Actions**:
    *   Force-pushing, deleting branches on remote, or any action that could lose work requires explicit confirmation.
    *   When uncertain about scope, ask clarifying questions before proceeding.

3.  **ALWAYS Clean Up Branches After PR Merge**:
    *   Delete the local branch and verify the remote branch is deleted immediately after every PR merge.
    *   Use `gh pr merge --squash --delete-branch` to auto-delete remote branches.
    *   Run `git fetch --prune origin` to clean up stale remote refs.
    *   See **After Your PR is Merged** section for the full checklist.

4.  **Self Improvements**:
    *   **Always** update `.agent/` documentation when learning a critical piece of information that will improve future agent performance. Workflow rules go in `AGENTS.md`; project knowledge goes in `project.md`.
    *   **Proactively suggest and implement meta-improvements**: Whenever you notice a repetitive task, recurring failures, or manual procedures that should be automated, suggest creating a new workflow (`.agent/workflows/`), an automation script (`scripts/`), or Git Hooks.
    *   **Always** run `/dump-context` before ending a session where significant work was done.

## Branch Management

**Default: Every task should result in a PR.** When the user asks you to do any work, the default assumption is that you will create a branch and open a PR for it. Each PR should typically be based on `origin/main` (independent PRs).

**Before starting new work, decide if you need a new branch:**
- Check if there's an existing PR for the current branch.
- If new work is unrelated to the current branch/PR, create a new branch from `origin/main`.
- When uncertain, ask the user or default to creating a new branch.

## Core Git Workflow

1.  **Sync with `main`**: Before starting new work, fetch the latest `main` branch.
    ```bash
    git fetch origin main
    ```

2.  **Create a Branch**: Create a new branch from `origin/main`. The branch MUST be prefixed with `agent/`.
    ```bash
    git checkout -b agent/your-branch-name origin/main
    ```

3.  **Implement Changes**: Make all code modifications according to the project's established conventions, as detailed in `project.md`.

4.  **Commit and Push**: Commit the changes with a clear message and push the branch.
    ```bash
    git add .
    git commit -m "feat: Describe the feature or fix"
    git push origin agent/your-branch-name
    ```

5.  **Create a Pull Request**: Open a pull request against the `main` branch. Direct pushes to `main` are prohibited. Use the `/create-pr` workflow for standardized title and body formatting.

## After Your PR is Merged

**MANDATORY: Always clean up branches immediately after a PR is merged.** Stale branches accumulate quickly and clutter the repository.

1.  **Switch to the `main` Branch**:
    ```bash
    git checkout main
    ```

2.  **Pull the Latest Changes**: Ensure your local `main` branch is up-to-date.
    ```bash
    git pull origin main
    ```

3.  **Delete the Local Branch**:
    ```bash
    git branch -d agent/your-branch-name
    ```

4.  **Delete the Remote Branch** (if `--delete-branch` was not used during merge):
    ```bash
    git push origin --delete agent/your-branch-name
    ```

5.  **Prune stale remote refs**:
    ```bash
    git fetch --prune origin
    ```

> **Tip:** Use `gh pr merge --squash --delete-branch` to auto-delete the remote branch on merge. You still must delete the local branch manually.

## Bash Best Practices

- **Avoid** shell control flow keywords (`for`, `while`, `until`, `if`/`then`/`else`/`fi`, `do`/`done`) in a single bash command. Prefer separate Bash tool calls instead.
- `&&`, `||`, `;`, and `|` (pipes) are allowed for simple chaining.
- **Example**: Instead of `for f in *.kt; do echo $f; done`, make separate bash tool calls.
- The `.claude/hooks/git-guardrails.sh` hook warns (but does not block) on shell control flow.

## Git Best Practices

- **Always** use non-interactive flags for commands that might open an editor (e.g., `git cherry-pick --continue --no-edit`). This prevents the shell from getting stuck waiting for user input.
- **Always** escape special characters in command arguments (e.g., `$` and `` ` ``) to prevent unintended shell expansion. Use single quotes or backslashes (`\`) for escaping.
- **NEVER** use heredoc subshells, multi-line quoted strings, or `&&`-chained write-then-use commands in `gh` or `git commit` commands. These cause Claude Code to require manual approval even when the command prefix is in the allow list, because permission patterns match only the **first command** in a chain.
- Instead: **use the Write tool** to create files in `.tmp/` (gitignored local temp directory), then issue a **standalone** Bash command referencing the file:
  - Commit: `Write` to `.tmp/commit-msg.txt`, then `Bash(git commit -F .tmp/commit-msg.txt)`
  - PR: `Write` to `.tmp/pr-body.txt`, then `Bash(gh pr create --title "..." --body-file .tmp/pr-body.txt)`
- This ensures each Bash call starts with an allowed prefix (`git commit`, `gh pr create`) with no chaining.
- **`.tmp/` files persist across sessions** — always `Read` the file first (even if it might not exist) before `Write`, otherwise Write will fail with "File has not been read yet".

## Task Management

Use `bd` (Beads CLI) for cross-session project tracking. See `project.md` for quick reference.

## File Index

| File | Contains | Read when |
|------|----------|-----------|
| `AGENTS.md` (this file) | Safety rules, git workflow, core processes | Always (entry point) |
| `project.md` | Project knowledge, domain, architecture, task management | Always (entry point) |
| `workflows/` | Step-by-step playbooks for common tasks | Specific operations |
