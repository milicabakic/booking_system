package com.griddynamics.lidlbooking.api.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EditUserWithJmbgFormDTO {

    @Schema(name = "username", description = "Username must be unique",
            example = "pera", required = true)
    private String username;

    @Schema(name = "password", example = "123", required = true)
    private String password;

    @Schema(name = "firstName", example = "Petar", required = true)
    private String firstName;

    @Schema(name = "lastName", example = "Petrovic", required = true)
    private String lastName;

    @Schema(name = "jmbg", example = "1234567891012", required = true)
    private String jmbg;

    @Schema(name = "email", description = "Email address must be valid and unique",
            example = "petarp@gmail.com", required = true)
    private String email;

    @Schema(name = "phoneNumber", description = "Phone number must be unique",
            example = "+381648417002", required = true)
    private String phoneNumber;
}
