# Temporal Spring Boot Demo

This example demonstrates how to use [Temporal.io](https://temporal.io/) with **Spring Boot (Java 21)** to orchestrate asynchronous and resilient processes using Workflows and Activities.
The project exposes a simple REST API to initiate processing of a request and query the result.

## 1- Architecture
```
Client (HTTP)
   |
   v
Spring Boot API
   |
Temporal Service  <->  PostgreSQL
   |
Worker (within the application itself)
```

Flow:
1. Client calls `POST /orders/{orderId}`
2. API starts a Workflow in Temporal
3. Worker executes the Activities
4. Result can be queried via `GET /orders/{workflowId}`

## 2- Rising the Temporal.io

Run the services:
```bash
docker compose up -d
```

Temporal UI will be available at:
```bash
http://localhost:8088
```
## 3- Run the application
```bash
mvn spring-boot:run
```