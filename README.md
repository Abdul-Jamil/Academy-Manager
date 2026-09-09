# Academy Manager

A production-ready, multi-branch educational ERP backend engine built using Java, Spring Boot, and **Domain-Driven Design (DDD)** principles. This project serves as a comprehensive portfolio piece demonstrating clean architecture, strict domain isolation, and containerized deployment.

---

## 🚀 Overview

**Academy Manager** is designed to handle complex administrative tasks for modern educational institutions spanning multiple geographic locations. It natively manages student enrollments, granular financial tracking (bills, payments, expenses, incomes), automated teacher scheduling, and employee payroll management within a multi-tenant, branch-isolated security model.

---

## 📂 Repository Structure (DDD Bounded Contexts)

The codebase is organized by business capability into bounded contexts, keeping domain logic isolated from external frameworks:

src/main/java/com/AjAkrampoor/Academy/
├── attendance/          # Student check-ins and session logging
├── bills/               # Invoicing, student payment processing, batch summaries
├── branches/            # Multi-branch infrastructure management
├── courses/             # Academic modules, timetables, and teacher assignments
├── enrollments/         # Student class registration and course transfers
├── expenses/            # Corporate outgoings and cash flow category tracking
├── income/              # Miscellaneous revenue tracking
├── inventory/           # Material supplies, vendor purchasing, and sales management
├── roles/               # RbAC framework (Permissions, custom access levels)
├── salaries/            # Complex staff contracts, session-rates, and monthly payrolls
├── sessions/            # Live classroom tracking and holiday schedule rules
├── students/            # Student profiles and guardian metadata CRM
├── users/               # Authentication credentials and user settings management
└── shared/              # Reusable Value Objects (Money, Description) & Security filters

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

## ⚠️ Portfolio Reflection & Technical Debt

This system was built primarily as a intensive learning vehicle. While it is highly structural and fully functional, software engineering is an iterative process of learning from your mistakes:

1. **Known Architectural Debt:** Looking back at the code, I recognize that I made a few sub-optimal structural decisions. For example, some data retrieval layers inside Response Assemblers introduce **N+1 over-fetching risks** under heavy loads instead of utilizing optimal database batching or batch-fetching strategies up front. Additionally, some use cases are heavily nested where decoupled **Domain Events** would have provided better systemic boundaries.
2. **Current Status:** Fixing these structural bottlenecks requires significant refactoring time. Because this project has achieved its primary goal of being a massive learning milestone, these fixes are deferred for a future version or a separate project. 
3. **What I’d Do Differently:** If I were to wipe the slate clean and rewrite this entire ecosystem from scratch today, I would change a great deal of the implementation.

---

## 💡 A Great Launchpad for Customization

Despite its retrospective flaws, **Academy Manager provides an incredibly solid, modular foundation**. 

Because the code is split cleanly into isolated bounded contexts (Attendance, Bills, Courses, Enrollments, Expenses, Income, Inventory, Salaries, Staff, Students, Users), you can easily extract individual modules or use this entire setup as a reliable base stack. It is highly customizable, strictly typed, and ready to be spun up locally in seconds.

---

## 🐳 Quick Start (Local Deployment)

This project is fully dockerized. To spin up the entire backend stack along with its database and network bridges locally, make sure you have Docker installed and run:

```bash
docker-compose up --build
```
