package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Payload for assigning roles to a user")
public class RoleAssignmentDto {

    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @Schema(description = "List of role IDs to assign", example = "[1, 2]")
    private List<Long> roleIds;
}

