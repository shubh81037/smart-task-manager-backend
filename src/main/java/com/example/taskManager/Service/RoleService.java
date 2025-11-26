package com.example.taskManager.Service;

import com.example.taskManager.exception.RoleNotFoundException;
import com.example.taskManager.exception.RoleFoundException;
import com.example.taskManager.model.Role;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;
import java.util.List;

/**
 * Service interface for role operations.
 * Implementations should provide transactional behavior where necessary.
 */
public interface RoleService {

    Role createRole(@NotBlank String name, String description) throws RoleFoundException;

    Role updateRole(@NonNull Long id, @NotBlank String name, String description) throws RoleNotFoundException, RoleFoundException;

    void deleteRole(@NonNull Long id) throws RoleNotFoundException;

    List<Role> getAllRoles();
}
