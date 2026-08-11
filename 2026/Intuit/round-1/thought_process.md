**Functional Requirements**

- `enqueue(Request)` is called to API Gateway which places it in a queue
- `getNextRequest()` is handled by a pool of background workers based on their availability
    - static pool of workers
- Implement the `RequestRouter` to serve **ALL** Enterprise requests before serving Free requests.
    - Other kind of tenants can be added later.


Out of scope
- pool of workers can be dynamic based on the load.


**Non-Functional Requirements**
- No request will be dropped and every request will be processed at-least-once.
- Handling spike of requests.