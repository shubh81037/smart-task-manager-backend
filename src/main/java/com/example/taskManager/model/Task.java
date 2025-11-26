package com.example.taskManager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task extends BaseModel 
{
        private String title;
        private String description;
        private LocalDateTime dueDate;
        
        @Enumerated(EnumType.STRING)
        private TaskStatus status;
        
        @ManyToOne
        @NotNull
        @JoinColumn(name = "user_id")
        @JsonManagedReference           //you want this side serializable
        private User assignedTo;
        
        @Enumerated(EnumType.STRING)
        private TaskPriority priority;
        
        // Getters and Setters
        // (Add standard getters and setters for all fields)
}
