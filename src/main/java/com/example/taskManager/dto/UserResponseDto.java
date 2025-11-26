package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Response model for user details")
public class UserResponseDto {

    @Schema(description = "User ID", example = "42")
    private Long id;

    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @Schema(description = "List of role names", example = "[\"USER\", \"ADMIN\"]")
    private List<String> roles;

    @Schema(description = "Active status of the user", example = "true")
    private boolean activeStatus;
}

