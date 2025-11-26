package com.example.taskManager.dto;


import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Response model for a task")
public class TaskResponseDTO {

    @Schema(description = "Task ID", example = "101")
    private long id;

    @Schema(description = "Task title", example = "Fix login bug")
    private String title;

    @Schema(description = "Task description", example = "Resolve JWT validation issue")
    private String description;

    @Schema(description = "Due date", example = "2025-11-20T14:00:00")
    private LocalDateTime dueDate;

    @Schema(description = "Task status", example = "PENDING")
    private TaskStatus status;

    @Schema(description = "Email of assigned user", example = "user@example.com")
    private String assignedToEmail;

    @Schema(description = "Task priority", example = "HIGH")
    private TaskPriority priority;
}

