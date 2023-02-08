package com.griddynamics.lidlbooking.domain.model.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingProviderStructure {

    private Long id;

    private String firstName;

    private String lastName;

    private String jmbg;

    private String email;

    private String phoneNumber;

}
