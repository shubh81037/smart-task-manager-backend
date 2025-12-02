# Architecture Overview

## Layers
- **Controller** — REST controllers (AuthController, RoleController, TaskController)
- **Service** — Business logic and validation
- **Repository** — Spring Data JPA repositories (UserRepository, RoleRepository, TaskRepository)
- **Database** — MySQL

## Request flow
Client -> Controller -> Service -> Repository -> MySQL
Authentication: JWT issued on login -> Client stores token -> Each request includes `Authorization: Bearer <token>` -> Jwt filter validates token and sets SecurityContext -> Controller methods use @AuthenticationPrincipal to get user

## Security
- JWT-based stateless authentication
- Method-level security via `@PreAuthorize("hasRole('ADMIN')")`
- Roles: `ROLE_USER`, `ROLE_ADMIN`

## Suggested deployment layout
- Application packaged as fat JAR
- Run behind Nginx with TLS termination
- Use managed MySQL service



