# 🧠 Smart Task Manager Backend

A secure, production-grade backend for managing tasks with role-based access, JWT authentication, and audit tracking — built with **Spring Boot** and **MySQL**.

---

## 🚀 Project Overview
This backend powers a task management platform where users can:
- Register, login, and manage personal tasks
- Filter, sort, and paginate task lists
- Update their profile securely
- Admins can assign roles, activate/deactivate users, and view users by role

Built with industry best practices — validation, exception handling, audit logging, and OpenAPI documentation.

---

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3
- Spring Security + JWT
- MySQL
- Spring Data JPA
- Swagger / OpenAPI

---

## ✨ Features
- JWT-based authentication & user registration
- Role-based access control (`USER`, `ADMIN`)
- Task CRUD with pagination, sorting, filtering
- Audit fields (`createdAt`, `updatedAt`)
- Admin features: role assignment, user activation, user listing
- Global exception handling
- Swagger UI

---

## ⚙️ Quick Setup (Local)

1. Clone the repository
```bash
git clone https://github.com/shubh81037/smart-task-manager-backend.git
cd smart-task-manager-backend
```

2. Configure `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

jwt.secret=your_jwt_secret_key
```

3. Create the database (manual if not using migrations)
```sql
CREATE DATABASE taskdb;
```

4. Run the app
```bash
mvn spring-boot:run
```

Backend will run at: `http://localhost:8082`

---

## 🧪 Admin Credentials for Testing
**👤 Admin Role:** `ROLE_ADMIN`  
**📧 Email:** `admin@example.com`  
**🔐 Password:** `admin123`

> Note: Create an initial admin user in DB if not Present and create ROLE_ADMIN if not present.

---

## 📄 API Documentation
- Swagger UI: `http://localhost:8082/swagger-ui/index.html`  
- OpenAPI Spec: `http://localhost:8082/v3/api-docs`

A machine-readable API spec (OpenAPI) is available at the /v3/api-docs endpoint when the app is running.

---

## 📚 API Endpoints (Summary)
For a detailed, structured list of endpoints (methods, paths, auth, DTOs, examples) see `docs/api-endpoints.md` (included with this project).  
Below is a short summary:

### Authentication — `/api/auth`
- `POST /api/auth/register` — Register new user (Public)  
- `POST /api/auth/login` — Login and get JWT token (Public)  
- `PUT /api/auth` — Update own profile (Requires Authentication)  
- `PUT /api/auth/admin` — Assign roles by email (Admin only)  
- `PUT /api/auth/admin/activeState/{id}?active=true|false` — Change user active state (Admin only)  
- `GET /api/auth/admin/users` — Get all users (Admin only)  
- `GET /api/auth/admin/userByRole?roleName=ROLE_USER` — Get users by role (Admin only)

### Roles — `/api/roles` (Admin)
- `POST /api/roles` — Create role  
- `PUT /api/roles/{id}` — Update role  
- `GET /api/roles` — List all roles  
- `DELETE /api/roles/{id}` — Delete role

### Tasks — `/api/tasks` (Authenticated users)
- `POST /api/tasks` — Create task for authenticated user  
- `GET /api/tasks` — Get paginated tasks with filters (`page`, `size`, `status`, `dueDate`, `priority`)  
- `PUT /api/tasks/{id}` — Update a user's task  
- `DELETE /api/tasks/{id}` — Delete a user's task

For full details and curl examples, open `docs/api-endpoints.md`.

---

## 🗂️ Folder Structure
```
src/
├── main/
│   ├── java/com/example/taskManager/
│   │   ├── Auth/                # Authentication controllers & services
│   │   ├── Controller/          # Role & Task controllers
│   │   ├── service/             # Business logic
│   │   ├── model/               # JPA entities (User, Role, Task, enums)
│   │   ├── dto/                 # Request & response DTOs
│   │   ├── repository/          # Spring Data JPA repositories
│   │   ├── security/            # JWT filters, SecurityConfig
│   │   └── utility/
│   └── resources/
│       └── application.properties
```

---

## 🏗️ Architecture & ER Diagram
A short architecture overview and an ER diagram are included in `docs/architecture.md` and `docs/er-diagram.md`.

---

## 🚀 Deployment Guide (Local & Production)
**Local:** ensure MySQL is running, configure properties, run with Maven or IDE.  
**Production (general guidance):**
- Use environment variables (never commit secrets)
- Use managed DB (RDS/GCP SQL/Azure)
- Use HTTPS + reverse proxy (Nginx)
- Build artifact: `mvn clean package` and deploy the generated JAR
- Consider containers (Docker) or cloud app services

---

## 📁 Docs Folder
This repository includes a `docs/` folder with:
- `api-endpoints.md` — full endpoint reference with examples
- `architecture.md` — architecture and auth flow
- `er-diagram.md` — ER diagram and SQL snippets
- `README-index.md` — short index of docs

---

## 👨‍💻 Author
**Shubham** — Aspiring backend developer focused on secure, scalable systems.  
🔗 https://github.com/shubh81037

---

