package com.griddynamics.lidlbooking.api.dto.users;

import lombok.Data;

@Data
public class BookingUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;

}
