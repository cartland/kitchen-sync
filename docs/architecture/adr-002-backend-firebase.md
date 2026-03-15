# ADR-002: Backend — Firebase/Firestore

## Status

Accepted (2026-03-15)

## Context

Kitchen Sync requires a backend for:
- User authentication (Google sign-in)
- Real-time data sync across household members
- Cloud storage for recipes, meal plans, and grocery lists

The team is small, so managing custom server infrastructure is undesirable. The app already requires Google sign-in (see product requirements), which aligns with Google's ecosystem.

## Decision

Use **Firebase** as the managed backend:

- **Firestore** for cloud data storage and real-time sync
- **Firebase Authentication** with Google sign-in
- **Firebase Security Rules** for access control (household-scoped)

No custom server code. All backend logic lives in security rules and client-side code.

## Consequences

- **Positive:** Zero server management. Firebase handles scaling, availability, and infrastructure.
- **Positive:** Built-in real-time listeners simplify the sync architecture. Firestore pushes changes to connected clients automatically.
- **Positive:** Firebase Auth integrates directly with Google sign-in, which is already the chosen auth method.
- **Positive:** Generous free tier covers early development and small-scale usage.
- **Negative:** Vendor lock-in to Google Cloud. Migration away from Firestore would require significant rework.
- **Negative:** Firestore's NoSQL document model requires denormalization and careful data modeling (no joins).
- **Negative:** Complex queries are limited compared to SQL. Data access patterns must be designed upfront.
- **Negative:** Security rules can become complex and hard to test as the data model grows.

## Alternatives Considered

- **Supabase (Postgres):** Open-source Firebase alternative with SQL. Strong option, but less mature real-time support and would require more backend management.
- **Custom backend (Ktor/Spring):** Maximum flexibility but significant infrastructure overhead for a small team. Premature for the current stage.
- **AWS Amplify:** Similar managed approach but less natural fit with Google sign-in and Android ecosystem.
