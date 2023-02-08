package com.griddynamics.lidlbooking.api.dto.users;

import lombok.Data;

@Data
public class BookingProviderDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;

}
