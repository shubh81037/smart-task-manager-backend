package com.example.taskManager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Base abstract model providing common attributes for entities.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected BaseModel() {
    }

    protected void setId(Long id) {
        this.id = id;
    }

    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    /*
    @PrePersist / @PreUpdate - are JPA life cycle annotation that runs custom logic automatically
    @PrePersist - trigger only once when a new entity insert into database (.save() method)
    @PreUpdate - trigger every time when any update is happening on existing entity

    * */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();       //custom logic
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();   //custom logic
    }



}