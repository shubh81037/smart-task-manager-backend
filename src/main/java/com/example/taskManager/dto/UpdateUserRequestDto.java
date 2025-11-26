package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload for updating user profile")
public class UpdateUserRequestDto {

    @Schema(description = "First name", example = "Shubham")
    private String firstName;

    @Schema(description = "Last name", example = "Verma")
    private String lastName;

    @Schema(description = "New password", example = "newSecurePassword123")
    private String password;
}

