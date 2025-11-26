package com.example.taskManager.Service;

import com.example.taskManager.exception.TaskNotFound;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Service interface for task operations.
 */
public interface TaskService {

    Task createTask(@NotBlank String title, String description, TaskStatus status, TaskPriority priority, @Future LocalDateTime dueDate, @NonNull String username);

    Page<Task> getTask(@NonNull String username, int page, int size, TaskStatus status, LocalDateTime dueDate, TaskPriority priority) throws TaskNotFound;

    Task updateTask(@NonNull String username, @NonNull Long id, @NotBlank String title, String description, TaskStatus status, TaskPriority priority, @Future LocalDateTime dueDate) throws Exception;

    void deleteTask(@NonNull String username, @NonNull Long id);
}
