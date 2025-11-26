
package com.example.taskManager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseModel {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
}
