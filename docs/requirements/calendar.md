# Calendar Integration

Google Calendar integration for communicating and recording meal plans.

## Goal

- Google Calendar integration
- Assign meals to specific days/times based on preferences
- Create calendar events to communicate what was decided
- Calendar serves as part of the historical record
- Calendar event contains recipe name as title only (no description or ingredients in the event)

## Status

Not started.

## Proposed

- Calendar selection: user picks which Google Calendar to write to (depends on API capabilities)
- Two-way sync: changes in Google Calendar reflected in the app (depends on API capabilities)
- Shared events: all household members invited to each meal event individually (depends on API capabilities)

## Deferred

## Not Doing

- In-app calendar view — *Reason: the Meal Plan Timeline is the in-app surface; the app pushes events to Google Calendar (push-only, decided 2026-07-02)*

## Open Questions

- Calendar selection, two-way sync, and shared events depend on Google Calendar API capabilities — finalize during implementation
