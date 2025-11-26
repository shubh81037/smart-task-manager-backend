package com.example.taskManager.dto;

import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Payload for creating or updating a task")
public class CreateTaskDTO {

    @NotBlank
    @Schema(description = "Task title", example = "Fix login bug")
    private String title;

    @Schema(description = "Task description", example = "Resolve JWT validation issue in login flow")
    private String description;

    @Future
    @Schema(description = "Due date in ISO format", example = "2025-11-20T14:00:00")
    private LocalDateTime dueDate;

    @Schema(description = "Task status", example = "PENDING")
    private TaskStatus status;

    @Schema(description = "Task priority", example = "HIGH")
    private TaskPriority priority;
}

