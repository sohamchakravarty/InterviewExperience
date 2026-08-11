**Design and implement an in-memory API request scheduler/router for a SaaS platform.**  As requests hit our API gateway, they are placed into this scheduler before being handed off to a limited pool of backend worker threads.

There are 2 different tiers of tenants (customers):

- **Enterprise Tenants:** Pay for premium service and get higher priority.
- **Free Tenants:** Get best-effort service.

Design the classes for `Request`, `Tenant`, and `RequestRouter` with the required high-level characteristics.

- Implement the `RequestRouter` to serve **ALL** Enterprise requests before serving Free requests.
- Implement two methods: `enqueue(Request)` and `getNextRequest()`.