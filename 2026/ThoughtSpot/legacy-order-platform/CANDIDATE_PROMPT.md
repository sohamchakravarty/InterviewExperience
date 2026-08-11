# Candidate Prompt

You are joining an incident-heavy area of a commerce platform. This service is a legacy Java order-processing service that was stable enough at low volume but is now causing operational issues.

Recent problems reported by product and operations:

- Duplicate order submissions sometimes create duplicate charges.
- Admin report generation becomes very slow as order volume grows.
- Memory usage keeps climbing during load tests.
- Some totals look slightly wrong, especially around discounts and shipping.
- The service is difficult to extend safely.

Your task:

1. Understand the existing code quickly.
3. Add support for idempotent order submission using the `X-Request-Id` header.
4. Add support for promo code `FREESHIP`, which should waive shipping charges only.
5. Fix the highest-risk production issues you find on the way.

Constraints:

- Treat this as a production system, not a toy kata.
- You may use AI assistance freely.
- Optimize for correctness, maintainability, and operational safety.
- You do not need to fully rewrite the service.
- Be explicit about what you would defer and why.

Artifacts available:

- A Postman collection and local environment are available in the `postman/` folder if you want to drive the API that way.
