package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Response after updating user profile")
public class UpdatedUserResponse {

    @Schema(description = "First name", example = "Shubham")
    private String firstName;

    @Schema(description = "Last name", example = "Verma")
    private String lastName;

    public UpdatedUserResponse(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

