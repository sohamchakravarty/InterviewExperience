# Legacy Order Platform

This folder contains a staff-level AI-assisted Java coding exercise.

The project is intentionally designed to feel like a slice of a real production service:

- Multiple files and packages.
- Legacy design choices layered over time.
- Hidden functional, concurrency, latency, memory, and maintainability issues.
- A feature request that cannot be solved well without understanding the existing code.

Start with [CANDIDATE_PROMPT.md](/Users/vishal.panjeta/Documents/work/thoughtspot/eureka/convex/ai-coding-round/legacy-order-platform/CANDIDATE_PROMPT.md).

Other content:

- [openapi/openapi.yaml](/Users/vishal.panjeta/Documents/work/thoughtspot/eureka/convex/ai-coding-round/legacy-order-platform/openapi/openapi.yaml): manual OpenAPI contract for the current service.
- [postman/legacy-order-platform.collection.json](/Users/vishal.panjeta/Documents/work/thoughtspot/eureka/convex/ai-coding-round/legacy-order-platform/postman/legacy-order-platform.collection.json): Postman collection with smoke tests and candidate acceptance tests.
- [postman/local.postman_environment.json](/Users/vishal.panjeta/Documents/work/thoughtspot/eureka/convex/ai-coding-round/legacy-order-platform/postman/local.postman_environment.json): local Postman environment pointed at `http://localhost:8080`.
- `src/main/java`: the actual codebase.
- `src/main/resources`: tiny config/template resources.

Build locally with:

```bash
cd ai-coding-round/legacy-order-platform
javac -d target/classes $(find src/main/java -name "*.java")
```

Run with:

```bash
cd ai-coding-round/legacy-order-platform
mkdir -p target/classes
javac -d target/classes $(find src/main/java -name "*.java")
cp -R src/main/resources/* target/classes/
java -cp target/classes com.interview.legacy.app.LegacyOrderApplication
```

Sample request:

```bash
curl -i -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId":"cust-1",
    "externalId":"web-1001",
    "currency":"USD",
    "promoCode":"SAVE10",
    "expedite":true,
    "items":[
      {"sku":"A-100","quantity":2,"unitPrice":19.95},
      {"sku":"B-200","quantity":1,"unitPrice":5.49}
    ]
  }'
```

Postman usage:

```bash
1. Import postman/legacy-order-platform.collection.json
2. Import postman/local.postman_environment.json
3. Start the service locally
4. Run the "Smoke Tests" folder against the local environment
5. Run the "Candidate Acceptance Tests" folder for checks around the requested behavior
```
