package com.griddynamics.lidlbooking.domain.model.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityStructure {

    private Long id;

    private String name;

    private String province;

    private CountryStructure country;

}
