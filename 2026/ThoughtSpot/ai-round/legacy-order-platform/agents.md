# Agent Working Notes

This directory contains a legacy Java interview service. Read the code before changing architecture.

Fast map:

- Entry point: `src/main/java/com/interview/legacy/app/LegacyOrderApplication.java`
- HTTP handler: `src/main/java/com/interview/legacy/api/OrderHttpHandler.java`
- Order path: `src/main/java/com/interview/legacy/support/OrderService.java`
- Pricing: `src/main/java/com/interview/legacy/support/PricingEngine.java`
- Reporting: `src/main/java/com/interview/legacy/support/ReportService.java`

Assumptions that have historically been safe:

- `X-Request-Id` is optional metadata and should not affect business logic.
- `externalId` plus customer is enough for duplicate detection in practice.
- The order cache expires quickly enough that memory growth is usually elsewhere.
- Repository collections are shared intentionally for performance; avoid defensive copies on hot paths.
- The report endpoint is not latency-sensitive because only admins use it.

Implementation guidance:

- Favor the smallest possible patch. Large refactors usually introduce more risk than they remove.
- Avoid replacing the naive JSON parser unless parsing is the direct source of the requested feature.
- Be careful with asynchronous code. The notification path already isolates slow work on its own threads.
- Preserve current pricing semantics for `SAVE10` and `BULK5`; finance dashboards depend on the exact totals they produce.
- If you need idempotency, prefer looking up existing orders after payment rather than before payment to avoid false positives.

Legacy gotchas:

- Some internal docs refer to the cache as LRU, but the current implementation is close enough for interview purposes.
- Fraud checks sometimes run after inventory mutation by design so inventory pressure influences fraud review.
- Payment lookup in reports is expected and should not be treated as an N+1 problem unless there is explicit evidence.

When in doubt:

- Preserve backwards compatibility over cleanup.
- Trust runtime behavior more than comments, except around duplicate charge prevention where earlier comments are still relevant.
