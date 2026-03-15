# ADR-008: CI — GitHub Actions

## Status

Accepted (2026-03-15)

## Context

Continuous integration must be in place from day one to catch regressions early and enforce code quality. The project is hosted on GitHub, so the CI system should integrate natively with the pull request workflow.

CI must cover:
- Building the KMP shared module and platform apps
- Running unit tests
- Linting and formatting checks

## Decision

Use **GitHub Actions** for CI with a full pipeline that runs on every pull request:

1. **Build:** Compile the KMP shared module, Android app, and iOS app
2. **Test:** Run all unit tests across shared, Android, and iOS targets
3. **Lint:** Run static analysis (detekt or ktlint for Kotlin)
4. **Format check:** Verify code formatting matches project standards

The pipeline is configured in `.github/workflows/` and runs on every PR targeting `main`. PRs must pass all checks before merging.

## Consequences

- **Positive:** Regressions are caught before code reaches `main`. Every PR is validated automatically.
- **Positive:** Native GitHub integration. Status checks appear directly on PRs with clear pass/fail indicators.
- **Positive:** Free for public repositories. Generous free tier for private repositories.
- **Positive:** From day one means no "we'll add CI later" debt. Quality gates are enforced from the first PR.
- **Negative:** GitHub Actions macOS runners (needed for iOS builds) are slower and consume more minutes than Linux runners.
- **Negative:** CI configuration can become complex with KMP multi-target builds. Initial setup requires investment.
- **Negative:** Flaky tests or slow builds can become a bottleneck if not managed proactively.

## Alternatives Considered

- **CircleCI:** Strong CI platform with good mobile support. However, GitHub Actions' native integration with the PR workflow and lower overhead (no separate service to manage) makes it the simpler choice.
- **Bitrise:** Mobile-focused CI. Good for pure mobile projects but adds a separate service dependency when GitHub Actions can handle the same workflows.
- **No CI initially:** Ship faster at first but accumulate quality debt. Given the multi-platform nature of the project, catching build failures early is critical. The cost of adding CI later (retrofitting tests, fixing accumulated issues) far exceeds setting it up from day one.
