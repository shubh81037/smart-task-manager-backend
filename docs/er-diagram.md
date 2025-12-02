# ER Diagram & Database Notes

Entities:
- **User**
  - id (PK)
  - first_name
  - last_name
  - email (unique)
  - password
  - active (boolean)
  - created_at
  - updated_at

- **Role**
  - id (PK)
  - name (e.g., ROLE_USER, ROLE_ADMIN)
  - description

- **Task**
  - id (PK)
  - title
  - description
  - status (enum)
  - priority (enum)
  - due_date (datetime)
  - assigned_to_id (FK -> user.id)
  - created_at
  - updated_at

Relationships:
- **User <-> Role** : Many-to-Many (user_roles junction table)
- **User 1 -> N Task** : One user can have many tasks (assigned_to_id)

## ASCII ER Diagram

```
+--------+          +-----------+         +--------+
|  User  |<--M:N--> | user_roles| <--N:1->|  Role  |
+--------+          +-----------+         +--------+
    |
    |1
    |            +--------+
    +--------->  | Task   |
                 +--------+
```

## Example SQL (simplified)

```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(150) UNIQUE,
  password VARCHAR(255),
  active BOOLEAN DEFAULT TRUE,
  created_at DATETIME,
  updated_at DATETIME
);

CREATE TABLE roles (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50),
  description VARCHAR(255)
);

CREATE TABLE user_roles (
  user_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE tasks (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255),
  description TEXT,
  status VARCHAR(50),
  priority VARCHAR(50),
  due_date DATETIME,
  assigned_to_id BIGINT,
  created_at DATETIME,
  updated_at DATETIME,
  FOREIGN KEY (assigned_to_id) REFERENCES users(id)
);
```


