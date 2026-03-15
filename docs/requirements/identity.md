# Users & Identity

Authentication and household membership model.

## Goal

- Google sign-in for authentication (aligns with Google Calendar integration)
- Household model: multiple members share recipes, plans, and shopping lists
- Each member has their own recipe ratings that aggregate into a household view (e.g., "2 of 3 liked it")
- Multiple households per user: users can belong to more than one household
- Active household concept: app shows one household at a time with a switcher UI
- Default household created on first launch (unnamed); household name required when sharing/inviting
- Roles per household: **Admin** and **Member**
  - Multiple Admins allowed; at least one Admin required per household
  - Admins can: generate/revoke invite links, remove members, delete the household
  - Members can: view and edit all shared data (recipes, plans, shopping lists)
- Invite links: multi-use, expire after 1 day, revocable by Admins
- Leaving a household: any member can leave voluntarily
  - Data (recipes, plans) stays with the household
  - Ratings by the departing member are removed
- Last Admin deletion: remaining Members are auto-promoted to Admin
- Orphaned household (no members remain): household is deleted

## Status

Not started.

## Proposed

## Deferred

## Not Doing

## Open Questions
