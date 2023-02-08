package com.griddynamics.lidlbooking.api.dto.structure;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountryStructureDTO {

    @Schema(name = "iso3", example = "ESP")
    private String iso3;

    @Schema(name = "name", example = "Spain")
    private String name;

}
