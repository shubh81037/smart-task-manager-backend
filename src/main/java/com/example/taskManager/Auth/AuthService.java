package com.example.taskManager.Auth;

import com.example.taskManager.dto.AuthResponse;
import com.example.taskManager.dto.UserResponseDto;
import com.example.taskManager.exception.EmailAlreadyRegistered;
import com.example.taskManager.exception.DefaultRoleNotFoundException;
import com.example.taskManager.exception.RoleNotFoundException;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for authentication-related operations.
 */
public interface AuthService {

    AuthResponse register(String firstName, String lastName, String email, String password) throws EmailAlreadyRegistered, DefaultRoleNotFoundException;

    AuthResponse login(String email, String rawPassword);

    // profile
    com.example.taskManager.model.User updateUserOwnProfile(String username, String password, String firstName, String lastName);

    void changeActiveState(Long id, boolean activeStatus);

    List<UserResponseDto> getAllUser();

    void assignRolesByEmail(String email, List<Long> roleIds) throws RoleNotFoundException;

    List<UserResponseDto> getUserByRoles(String roleName) throws RoleNotFoundException;
}

