package com.griddynamics.lidlbooking.api.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EditUserFormDTO {

    @Schema(name = "username", example = "iivanovic",
            description = "Username must be unique", required = true)
    private String username;

    @Schema(name = "password", example = "123", required = true)
    private String password;

    @Schema(name = "firstName", example = "Ivan", required = true)
    private String firstName;

    @Schema(name = "lastName", example = "Ivanovic", required = true)
    private String lastName;

    @Schema(name = "email", example = "ivanivanovic89@gmail.com",
            description = "Email address must be valid and unique", required = true)
    private String email;

    @Schema(name = "phoneNumber", example = "+3816487864",
            description = "Phone number must be valid and unique", required = true)
    private String phoneNumber;

}
