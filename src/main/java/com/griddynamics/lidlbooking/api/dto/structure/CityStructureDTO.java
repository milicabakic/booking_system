package com.griddynamics.lidlbooking.api.dto.structure;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityStructureDTO {

    @Schema(name = "id", example = "14542")
    private Long id;

    @Schema(name = "name", example = "Lloret de Mar")
    private String name;

    @Schema(name = "province", example = "Catalonia")
    private String province;

    private CountryStructureDTO country;

}
