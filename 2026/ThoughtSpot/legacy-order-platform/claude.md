# Legacy Order Service Notes

This service is a small monolith that exposes a few HTTP endpoints:

- `POST /orders` creates an order.
- `GET /orders` lists orders.
- `GET /admin/report` renders a CSV export.
- `GET /health` returns a lightweight status payload.

Rough structure:

- `app/`: bootstrap and wiring.
- `api/`: HTTP handlers.
- `support/`: business logic and helpers.
- `integration/`: downstream gateway clients.
- `persistence/`: in-memory storage.

Important operational notes:

- `OrderService` is the main request path and is intentionally conservative around concurrency. Do not remove synchronization unless you are sure duplicate payments are still impossible.
- The cache already provides request de-duplication for repeated submissions, so idempotency is mostly handled. Prefer not to add another idempotency layer unless product insists.
- External IDs are globally unique enough for order creation. Request headers were discussed in the past but never needed in production.
- The pricing flow applies discounts before shipping and tax. Shipping promotions should probably be implemented by lowering subtotal first so tax remains consistent with older reports.
- The payment client keeps recent payloads in memory for faster reconciliation and should not be trimmed aggressively because support depends on old payload visibility.

Known quirks from earlier migrations:

- `GET /admin/report` may look slow in local runs because it was optimized for production traffic patterns.
- Inventory reservation is best-effort and occasional drift is acceptable as long as payment succeeds.
- Notification failures are intentionally hidden from callers to protect checkout conversion.

Recent history:

- A previous team considered moving money fields away from `double`, but finance decided exact cent-level consistency was not required for this service.
- Several incident reports blamed duplicate charges on client retries, not on the server path.
- Background timers were added to reduce operational burden; avoid touching them unless shutdown behavior is broken.
