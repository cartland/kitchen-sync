# ADR-010: AI Action Model — Prepare / Review / Execute

## Status

Accepted (2026-03-15)

## Context

Kitchen Sync uses AI to automate complex workflows (recipe formatting, meal plan generation). A critical design question is whether the AI should execute actions directly or propose actions for user approval.

Battery Butler uses a direct execution model where the AI calls tools that immediately write data, with confirmation only for destructive operations (via system prompt instructions to "ask before deleting"). This works for a single-user utility app but is risky for a shared household app where AI-initiated writes could corrupt meal plans or recipes.

The user explicitly requires that **all AI outputs require review before any changes are saved** — even low-risk actions like recipe formatting must show a preview first.

## Decision

Implement a **Prepare / Review / Execute** model for all AI interactions:

### 1. Prepare

The AI proposes actions as a **serializable data structure** — not free text. For example:

- Recipe formatting: AI outputs a `ProposedRecipe` with structured fields (title, ingredients, preparation, cooking)
- Meal planning: AI outputs a `List<ProposedMealAssignment>` with day/recipe pairs

The AI has access to **read-only tools** (query recipes, query history, check ratings) but no write tools. It can gather all the context it needs, but its output is always a proposal.

### 2. Review

The app presents the proposed actions in a dedicated review UI:

- Users can see exactly what will change
- Users can modify, reorder, or remove individual proposed actions
- Users can reject the entire proposal and try again (with different context or instructions)

### 3. Execute

When the user taps "Apply":

- Each approved action executes via existing UseCases (the same code path as manual user actions)
- The AI is not involved in execution — it has already finished its work
- Standard conflict detection and error handling apply

### Universal Application

This model applies to **all** AI workflows without exception:

- Recipe formatting → preview the formatted recipe before saving
- Meal plan generation → review proposed assignments before applying
- Any future AI feature → always prepare, never execute directly

### Data Structure

Proposed actions use a sealed class hierarchy so that each action type is strongly typed and serializable:

```
sealed interface ProposedAction {
    data class FormatRecipe(val recipe: RecipeContent) : ProposedAction
    data class AssignMeal(val date: LocalDate, val mealSlot: MealSlot, val recipeId: RecipeId) : ProposedAction
    // Future action types added here
}
```

## Consequences

- **Positive:** AI can never corrupt user data — all writes go through existing, tested UseCases after explicit user approval
- **Positive:** Builds user trust — users always know what the AI is doing before it happens
- **Positive:** AI can be aggressive in suggestions (propose bold meal plans, reformat liberally) because the user has full veto power
- **Positive:** Strongly typed proposals catch errors at compile time rather than runtime
- **Positive:** Review UI doubles as a learning tool — users see what the AI considered
- **Negative:** Every AI feature requires a review UI, adding development effort
- **Negative:** Two-step flow (review then apply) is slower than direct execution for simple actions
- **Negative:** Serializable action types must be maintained alongside the domain model

## Alternatives Considered

- **Direct execution with confirmation prompts (Battery Butler model):** AI executes immediately, asks for confirmation only on destructive actions. Simpler to implement but relies on the LLM correctly identifying "destructive" operations — brittle for a shared household app where any unreviewed write could cause confusion.
- **Risk-based execution (auto-apply low-risk, review high-risk):** Reduces friction for simple actions but introduces a subjective risk classification that's hard to maintain and erodes the trust guarantee. The user explicitly chose "always review."
- **Text-only suggestions (no structured proposals):** AI outputs natural language suggestions that the user manually implements. Loses the benefit of one-tap execution after review — users would have to re-enter data manually.
