package com.griddynamics.lidlbooking.domain.model;

import com.griddynamics.lidlbooking.domain.model.structure.CountryStructure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City {

    private Long id;

    private String name;

    private String province;

    private CountryStructure country;

}
