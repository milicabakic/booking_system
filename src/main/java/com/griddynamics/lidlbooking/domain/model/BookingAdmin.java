package com.griddynamics.lidlbooking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingAdmin {

    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    public BookingAdmin(final String firstName, final String lastName, final String phoneNumber,
                        final String email, final String username, final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
