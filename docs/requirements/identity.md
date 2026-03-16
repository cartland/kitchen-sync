# Users & Identity

Authentication and household membership model.

## Goal

- Google sign-in for authentication (aligns with Google Calendar integration)
- Household model: multiple members share recipes, plans, and shopping lists
- **Single household per user for MVP**; multi-household support deferred
- Default household created on first launch (unnamed); household name required when sharing/inviting
- Roles per household: **Owner** (single) and **Member**
  - Owner can: generate invite links
  - Members can: view and edit all shared data (recipes, plans, shopping lists)
- Invite links: simple shareable links (no expiry or revocation for MVP)
- Leaving a household: any member can leave voluntarily
  - Data (recipes, plans) stays with the household

## Status

Not started.

## Proposed

## Deferred

- **Multiple households per user**: Users belong to more than one household with active household switcher — deferred; single household for MVP
- **Admin/Member role system**: Multiple admins, role changes, member removal, auto-promotion on last admin departure — deferred; single owner + members for MVP
- **Invite link expiry and revocation**: 1-day expiry, revocable by admins — deferred; simple non-expiring links for MVP
- **Member removal by admin**: Admins can remove members from household — deferred
- **Rating removal on departure**: Per-user ratings removed when member leaves — deferred along with ratings feature
- **Orphaned household cleanup**: Auto-delete household when no members remain — deferred

## Not Doing

## Open Questions
