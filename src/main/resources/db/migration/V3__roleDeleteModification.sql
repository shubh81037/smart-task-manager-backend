ALTER TABLE users_roles
DROP FOREIGN KEY users_roles_ibfk_2;

ALTER TABLE users_roles
ADD CONSTRAINT fk_user_roles_role
FOREIGN KEY (role_id) REFERENCES roles(id)
ON DELETE CASCADE;

