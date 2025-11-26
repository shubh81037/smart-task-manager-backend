package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Payload for user registration")
public class RegisterRequest {

    @Schema(description = "First name", example = "Shubham")
    private String firstName;

    @Schema(description = "Last name", example = "Verma")
    private String lastName;

    @Schema(description = "Email address", example = "shubham@example.com")
    private String email;

    @Schema(description = "Password", example = "strongPassword123")
    private String password;
}

