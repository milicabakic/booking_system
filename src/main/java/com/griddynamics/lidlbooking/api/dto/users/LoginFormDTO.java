package com.griddynamics.lidlbooking.api.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginFormDTO {

    @Schema(name = "username", example = "pera", required = true)
    private String username;

    @Schema(name = "password", example = "123", required = true)
    private String password;

}
