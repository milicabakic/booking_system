package com.griddynamics.lidlbooking.api.dto;

import com.griddynamics.lidlbooking.api.dto.structure.ServiceFacilityStructureDTO;
import com.griddynamics.lidlbooking.commons.BedType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudioDTO {

    private Long id;

    @Schema(name = "name", example = "Room 202", required = true)
    @NotBlank
    private String name;

    @Schema(name = "numberOfRooms", example = "1", required = true)
    @Min(1)
    private Integer numberOfRooms;

    @Schema(name = "numberOfPeople", example = "3", required = true)
    @Min(1)
    private Integer numberOfPeople;

    @Schema(name = "beds", example = "[\"twin\", \"single\"]")
    @Size(min = 1)
    private BedType[] beds;

    @NotNull
    private ServiceFacilityStructureDTO serviceFacility;

    private String classificationType;


    public StudioDTO(final String name, final Integer numberOfRooms, final Integer numberOfPeople,
                     final BedType[] beds, final ServiceFacilityStructureDTO serviceFacility) {
        this.name = name;
        this.numberOfRooms = numberOfRooms;
        this.numberOfPeople = numberOfPeople;
        this.beds = beds;
        this.serviceFacility = serviceFacility;
    }
}
