package com.griddynamics.lidlbooking.domain.model.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFacilityStructure {

    private Long id;

    private String address;

    private String postalCode;

    private Integer numberOfFloors;

    private String name;

    private String contactPhone;

    private String description;

    private OffsetTime checkinTime;

    private OffsetTime checkoutTime;

}
