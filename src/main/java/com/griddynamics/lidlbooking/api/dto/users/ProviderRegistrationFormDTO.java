package com.griddynamics.lidlbooking.api.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderRegistrationFormDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String email;
    private String phoneNumber;
}
