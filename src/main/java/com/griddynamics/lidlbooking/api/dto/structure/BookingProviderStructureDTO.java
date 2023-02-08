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
public class BookingProviderStructureDTO {

    @Schema(name = "id", example = "1")
    private Long id;

    @Schema(name = "firstName", example = "Pera")
    private String firstName;

    @Schema(name = "lastName", example = "Peric")
    private String lastName;

    @Schema(name = "jmbg", example = "1231231231234")
    private String jmbg;

    @Schema(name = "email", example = "pera@gmail.com")
    private String email;

    @Schema(name = "phoneNumber", example = "+381 63 000 0000")
    private String phoneNumber;

    public BookingProviderStructureDTO(final Long id) {
        this.id = id;
    }

}
