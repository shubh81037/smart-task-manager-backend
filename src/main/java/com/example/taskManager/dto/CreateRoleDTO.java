package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Payload for creating or updating a role")
public class CreateRoleDTO {

    @NotBlank
    @Schema(description = "Role name", example = "ADMIN")
    private String name;

    @Schema(description = "Role description", example = "Administrator with full access")
    private String description;
}

