This project is a Redis-based Distributed Rate Limiter built using Spring Boot to control how many requests a client can send to an API within a specific time window.

The rate limiter supports two industry-standard algorithms:

Fixed Window

Token Bucket

The active algorithm is selected using configuration, so no code change is required to switch between them.

The system also supports Per-API different limits, where each API endpoint can have its own request limit and time window. If a request comes to an API that does not have a specific rule, a default rule is applied.

All incoming requests are intercepted using a Spring OncePerRequestFilter, where a unique Redis key is generated using API path, user, and IP address. This ensures:

Per-user throttling

Per-IP protection

Distributed safety across multiple instances

The rate limit validation happens in Redis using atomic operations, making the system thread-safe and scalable. If a request exceeds the allowed limit, the API responds with HTTP 429 (Too Many Requests).

This project demonstrates real-world backend concepts like:

Distributed systems

Caching with Redis

Request throttling

Clean architecture using services and filters

Configuration-driven behavior
