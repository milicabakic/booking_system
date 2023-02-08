package com.griddynamics.lidlbooking.api.dto;

import com.griddynamics.lidlbooking.api.dto.structure.AmenityStructureDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmenityDTO {

    @Schema(name = "id", nullable = true)
    private Long id;

    @Schema(name = "name", example = "Shared bathroom")
    @NotBlank
    private String name;

    private AmenityStructureDTO parent;

    public AmenityDTO(final String name, final AmenityStructureDTO parent) {
        this.name = name;
        this.parent = parent;
    }

    public AmenityDTO(final String name) {
        this.name = name;
    }

}
