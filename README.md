# Academy Manager

A production-ready, multi-branch educational ERP backend engine built using Java, Spring Boot, and **Domain-Driven Design (DDD)** principles. This project serves as a comprehensive portfolio piece demonstrating clean architecture, strict domain isolation, and containerized deployment.

---

## 🚀 Overview

**Academy Manager** is designed to handle complex administrative tasks for modern educational institutions spanning multiple geographic locations. It natively manages student enrollments, granular financial tracking (bills, payments, expenses, incomes), automated teacher scheduling, and employee payroll management within a multi-tenant, branch-isolated security model.

---

## 📂 Repository Structure (DDD Bounded Contexts)

The codebase is organized strictly by business capability into distinct bounded contexts, decoupling domain models from infrastructure concerns:

```text
src/main/java/com/AjAkrampoor/Academy/
├── attendance/          # Student check-ins and attendance logging
├── bills/               # Invoicing, student payment processing, and fast lookups
├── branches/            # Multi-branch entity and state management
├── courses/             # Academic classes, timetables, and teacher assignments
├── enrollments/         # Course registration workflow and lifecycle tracking
├── expenses/            # Cash flow outgoings and expense classification
├── income/              # Miscellaneous revenue tracking
├── inventory/           # Material supplies, vendor purchasing, and sales
├── roles/               # Granular Role-based Access Control (RBAC) permissions
├── salaries/            # Complex payroll, fixed/per-session rate contracts
├── sessions/            # Classroom timeline tracking and holiday scheduling
├── students/            # Student profiles and guardian CRM metadata
├── users/               # Authentication, credentials, and locale settings
└── shared/              # Global Immutable Value Objects (Money, Name) & JWT Filters
```

---

## 🛠️ Architecture & Tech Stack

- **Framework:** Spring Boot (Data JPA, Security)
- **Database:** Relational (configured via Hibernate)
- **Security:** Stateless JWT authentication with HTTP-only cookies and fine-grained, permission-based access control (`@PreAuthorize`)
- **DevOps:** Fully containerized via **Docker** and **Docker Compose** for instant local environment replication
- **Design Pattern:** Domain-Driven Design (DDD) featuring:
  - Strict separation of Domain Models from Infrastructure Entities via custom Mappers.
  - Encapsulated Aggregates protecting domain invariants.
  - Immutable Value Objects (`Money`, `Name`, `PhoneNumber`, `Description`) to eliminate primitive obsession.
  - Application services isolated into single-responsibility Use Cases.

---

## ⚡ Architectural Trade-offs & Production Scaling Roadmap

This system was engineered with a strict domain-driven foundation to prioritize data integrity and clean code boundaries. In a production enterprise environment scaling to thousands of concurrent multi-branch requests, the following optimization roadmap is planned to maximize throughput and minimize latency:

* **Data Fetching Optimization (N+1 Resolution):** Current response layers leverage traditional Hibernate entity mappings. To eliminate over-fetching during high-volume reporting cycles, read-heavy operations are scheduled to be optimized using **Entity Graphs** and specialized **DTO projections** via JPQL to batch data requirements into singular, highly optimized database round-trips.
* **Asynchronous Domain Event Decoupling:** Tightly coupled transactional use cases (such as triggering ledger records upon payroll execution) will be refactored from synchronous processing into an **Event-Driven Architecture**. Transitioning to Spring Application Events (and eventually a message broker like RabbitMQ) will ensure eventual consistency and drastically lower API response times.
* **Distributed Microservices Migration:** Thanks to the strict decoupling of the current bounded contexts (`bills`, `salaries`, `inventory`), the application layout is strategically positioned for horizontal scaling. Individual high-load domains can be cleanly extracted into independent microservices with their own isolated database schemas with minimal system disruption.

---

## 💡 A Great Launchpad for Customization

Despite its retrospective flaws, **Academy Manager provides an incredibly solid, modular foundation**. 

Because the code is split cleanly into isolated bounded contexts, you can easily extract individual modules or use this entire setup as a reliable base stack.

---

## 🐳 Quick Start (Local Deployment)

To spin up the entire backend stack along with its database, make sure you have Docker installed and run:

```bash
docker-compose up --build
```
