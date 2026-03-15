# ADR-009: AI Integration Architecture

## Status

Accepted (2026-03-15)

## Context

Kitchen Sync plans extensive use of LLMs for complex workflows such as recipe formatting (paste freeform text, get structured Markdown) and meal plan generation (AI queries recipes, history, and ratings to propose a weekly plan). The app needs an AI layer that:

1. Integrates cleanly with the existing Clean Architecture (domain → usecase → data → presentation)
2. Supports multiple LLM backends (cloud and on-device)
3. Exposes domain capabilities as tools the LLM can call
4. Keeps AI concerns isolated from the rest of the codebase

Battery Butler (`/tmp/battery-butler/`) provides a proven reference implementation of this pattern using `AiEngine`, `ToolHandler`, and tool definitions.

## Decision

### AI Module Structure

Introduce an `:ai` module at the data layer that implements AI engine interfaces defined in `:domain`:

```
domain (AiEngine interface, AiMessage, ToolHandler)
  ↑
usecase (AI-powered use cases consume AiEngine + domain tools)
  ↑
ai (Gemini cloud engine, on-device ML Kit engine, NoOp engine)
```

### AiEngine Interface

Define `AiEngine` in the domain layer as a platform-agnostic interface:

- `generateResponse(prompt: String, toolHandler: ToolHandler?): Flow<AiMessage>` — streams partial and final responses
- `isAvailable: Flow<Boolean>` — reports whether the engine is ready
- Engine selection at runtime (cloud, on-device, or disabled)

### Two Implementations

1. **Gemini (cloud)**: Google's Generative AI SDK with native function calling. Primary engine when online.
2. **On-device (ML Kit)**: For offline use and privacy-sensitive operations. Manual tool calling via JSON in system prompt.

### ToolHandler Pattern

Domain capabilities are exposed to the LLM as named tools:

- `fun interface ToolHandler { suspend fun execute(name: String, args: Map<String, Any?>): String }`
- Tool names and parameter keys defined as constants (e.g., `AiToolNames`, `AiToolParams`)
- Each tool maps to an existing UseCase — the AI calls tools, tools call UseCases
- Tools are read-only query tools (the AI never writes data directly; see ADR-010)

### AI Isolation

- Presentation layer never imports `:ai` directly
- All AI interactions flow through UseCases (e.g., `FormatRecipeUseCase`, `GenerateMealPlanUseCase`)
- If AI is unavailable, the app remains fully functional — AI features degrade gracefully

## Consequences

- **Positive:** Clean separation — AI is a pluggable capability, not a dependency
- **Positive:** Offline support via on-device engine; cloud engine for higher quality
- **Positive:** ToolHandler reuses existing UseCases — no duplication of business logic
- **Positive:** Engine selection at runtime allows user preference and graceful fallback
- **Negative:** Two engine implementations to maintain (Gemini SDK and ML Kit)
- **Negative:** On-device engine has lower quality and manual JSON tool parsing
- **Negative:** Tool definitions must be kept in sync with UseCase signatures

## Alternatives Considered

- **Cloud-only (Gemini):** Simpler, but breaks the offline-first requirement. Users without connectivity lose AI features entirely.
- **Provider-agnostic (support any LLM):** More flexible but premature — Gemini aligns with the Firebase/Google ecosystem and on-device ML Kit covers offline. Can abstract further if needed later.
- **AI in presentation layer:** Would couple UI to AI concerns and make testing harder. Routing through UseCases keeps the architecture clean.
