package com.griddynamics.lidlbooking.api.dto.structure;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFacilityStructureDTO {

    @Schema(name = "id", example = "1")
    private Long id;

    private String address;

    private String postalCode;

    private Integer numberOfFloors;

    private String name;

    private String contactPhone;

    private String description;

    private OffsetTime checkinTime;

    private OffsetTime checkoutTime;

    public ServiceFacilityStructureDTO(final Long id) {
        this.id = id;
    }
}
