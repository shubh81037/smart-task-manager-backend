package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "User login credentials")
public class LoginRequest {

    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @Schema(description = "User password", example = "securePassword123")
    private String password;
}


