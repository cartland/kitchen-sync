# AI Features

AI-powered workflows using LLMs with the Prepare / Review / Execute model.

## Design Principles

- **Build without AI first**: Every feature works as a manual UseCase before being exposed as an AI tool
- **AI proposes, user disposes**: All AI outputs are proposals that require explicit user approval before saving
- **Read-only AI tools**: The AI can query data (recipes, history, ratings) but never writes directly
- **Graceful degradation**: If AI is unavailable (offline without on-device model, or disabled), all features remain fully functional via manual input

## Goal

- **Recipe formatting**: Paste freeform text → AI reformats into restricted Markdown (Title, Ingredients, Preparation, Cooking) → user reviews preview → approves to save
- **Meal plan generation**: AI queries recipe list, history, ratings, and variety data → proposes a weekly plan of day/recipe assignments → user reviews and edits → approves to apply
- **Prepare / Review / Execute model**: All AI actions require user review; AI never writes data directly (see ADR-010)
- **Dual engine support**: Gemini (cloud) for online use, on-device ML Kit for offline/privacy (see ADR-009)
- **Tool-based architecture**: Domain capabilities exposed to the LLM as typed tools via ToolHandler interface (see ADR-009)

## Status

Not started.

## Proposed

## Deferred

- Shopping list optimization (AI suggests grouping, substitutions)
- Recipe suggestions based on pantry contents
- Dietary constraint learning from household rating patterns

## Not Doing

- Direct AI execution without user review — *Reason: user data safety; all AI outputs must be reviewed*
- AI-generated recipe content (writing recipes from scratch) — *Reason: manual entry only for now*

## Open Questions
