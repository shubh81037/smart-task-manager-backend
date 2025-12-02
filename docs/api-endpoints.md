# API Endpoints — Detailed Reference

Generated from controllers in the project.

## Authentication Controller — `/api/auth`

### POST /api/auth/register
- **Description:** Register a new user.
- **Auth:** Public
- **Request Body:** `RegisterRequest` (firstName, lastName, email, password)
- **Response:** `AuthResponse` (token, user info)
- **Example:
```bash
curl -X POST http://localhost:8082/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@example.com","password":"pass123"}'
```

### POST /api/auth/login
- **Description:** Login and receive JWT token.
- **Auth:** Public
- **Request Body:** `LoginRequest` (email, password)
- **Response:** `AuthResponse` (token, user info)
- **Example:
```bash
curl -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"admin123"}'
```

### PUT /api/auth
- **Description:** Update own profile (firstName, lastName, password optional).
- **Auth:** Authenticated user (JWT)
- **Parameters:** `firstName`, `lastName`, `password` as request params.
- **Response:** `UpdatedUserResponse`
- **Example:
```bash
curl -X PUT "http://localhost:8082/api/auth?firstName=New&lastName=Name" \
  -H "Authorization: Bearer <TOKEN>"
```

### PUT /api/auth/admin
- **Description:** Assign roles to a user by email.
- **Auth:** Admin only
- **Request Body:** `RoleAssignmentDto` (email, roleIds)
- **Example:
```bash
curl -X PUT http://localhost:8082/api/auth/admin \
  -H "Authorization: Bearer <ADMIN_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","roleIds":[1,2]}'
```

### PUT /api/auth/admin/activeState/{id}?active=true|false
- **Description:** Change user active/inactive state (Admin only)
- **Auth:** Admin only
- **Example:
```bash
curl -X PUT "http://localhost:8082/api/auth/admin/activeState/5?active=false" \
  -H "Authorization: Bearer <ADMIN_TOKEN>"
```

### GET /api/auth/admin/users
- **Description:** Get all users.
- **Auth:** Admin only

### GET /api/auth/admin/userByRole?roleName=ROLE_USER
- **Description:** Get users by role name.
- **Auth:** Admin only

---

## Roles Controller — `/api/roles` (Admin)

### POST /api/roles
- **Description:** Create a new role.
- **Auth:** Admin only
- **Request Body:** `CreateRoleDTO` (name, description)

### PUT /api/roles/{id}
- **Description:** Update an existing role.
- **Auth:** Admin only
- **Params:** `roleName`, `description` (optional request params)

### GET /api/roles
- **Description:** List all roles.
- **Auth:** Admin only

### DELETE /api/roles/{id}
- **Description:** Delete a role by ID.
- **Auth:** Admin only

---

## Tasks Controller — `/api/tasks` (Authenticated users)

### POST /api/tasks
- **Description:** Create a new task for the authenticated user.
- **Auth:** Authenticated user
- **Request Body:** `CreateTaskDTO` (title, description, status, priority, dueDate)
- **Response:** `TaskResponseDTO`
- **Example:
```bash
curl -X POST http://localhost:8082/api/tasks \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","description":"Desc","status":"OPEN","priority":"HIGH","dueDate":"2025-12-10T10:00:00"}'
```

### GET /api/tasks
- **Description:** Get paginated tasks with optional filters.
- **Auth:** Authenticated user
- **Query params:** `page` (default 0), `size` (default 5), `status`, `dueDate` (ISO datetime), `priority`
- **Response:** JSON with `tasks`, `currentPage`, `totalPage`, `totalItems`
- **Example:
```bash
curl -X GET "http://localhost:8082/api/tasks?page=0&size=5" \
  -H "Authorization: Bearer <TOKEN>"
```

### PUT /api/tasks/{id}
- **Description:** Update a task owned by the authenticated user.
- **Auth:** Authenticated user
- **Request Body:** `CreateTaskDTO`
- **Example:
```bash
curl -X PUT http://localhost:8082/api/tasks/12 \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated","description":"New","status":"IN_PROGRESS"}'
```

### DELETE /api/tasks/{id}
- **Description:** Delete a task owned by the authenticated user.
- **Auth:** Authenticated user
- **Example:
```bash
curl -X DELETE http://localhost:8082/api/tasks/12 \
  -H "Authorization: Bearer <TOKEN>"
```




## Example Request/Response Bodies (Updated with all enum values)

### Task Creation Example Request
```json
{
  "title": "sample_title",
  "description": "sample_description",
  "status": [
    "PENDING",
    "IN_PROGRESS",
    "COMPLETED",
    "CANCELLED"
  ],
  "priority": [
    "LOW",
    "MEDIUM",
    "HIGH"
  ],
  "dueDate": "2025-12-10T10:00:00"
}
```

### Task Response Example
```json
{
  "id": 123,
  "title": "sample_title",
  "description": "sample_description",
  "status": [
    "PENDING",
    "IN_PROGRESS",
    "COMPLETED",
    "CANCELLED"
  ],
  "priority": [
    "LOW",
    "MEDIUM",
    "HIGH"
  ],
  "assignedToEmail": "user@example.com",
  "dueDate": "2025-12-10T10:00:00"
}
```

