package com.griddynamics.lidlbooking.api.dto;

import com.griddynamics.lidlbooking.api.dto.structure.BookingProviderStructureDTO;
import com.griddynamics.lidlbooking.api.dto.structure.CityStructureDTO;
import com.griddynamics.lidlbooking.api.dto.structure.CountryStructureDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFacilityDTO {

    @Schema(name = "id", nullable = true)
    public static final int FIVE = 5;
    public static final int TEN = 10;
    public static final int TWELVE = 12;
    public static final int TWENTY = 20;
    private Long id;

    @Schema(name = "address", example = "Carrer de la Riera, 77", required = true)
    @NotBlank
    private String address;

    @NotNull
    private CityStructureDTO city;

    @NotNull
    private CountryStructureDTO country;

    @Schema(name = "postalCode", example = "17310", required = true)
    @NotBlank
    @Size(min = FIVE, max = TEN)
    private String postalCode;

    @Schema(name = "numberOfFloors", example = "9", required = true)
    @Min(1)
    private Integer numberOfFloors;

    @Schema(name = "name", example = "Hotel Don Juan", required = true)
    @NotBlank
    private String name;

    @Schema(name = "contactPhone", example = "+34972365700", required = true)
    @NotBlank
    @Size(min = TWELVE, max = TWENTY)
    private String contactPhone;

    @Schema(name = "description", example =
            "This contemporary hotel with a landscaped entrance is 400 m from the beach",
            required = true)
    @NotBlank
    private String description;

    @Schema(name = "checkinTime", example = "12:00:00+01:00")
    private OffsetTime checkinTime;

    @Schema(name = "checkoutTime", example = "10:00:00+01:00")
    private OffsetTime checkoutTime;

    @NotNull
    private BookingProviderStructureDTO bookingProvider;


    public ServiceFacilityDTO(final String address, final CityStructureDTO city, final CountryStructureDTO country,
                              final String postalCode, final Integer numberOfFloors, final String name,
                              final String contactPhone, final String description, final OffsetTime checkinTime,
                              final OffsetTime checkoutTime, final BookingProviderStructureDTO bookingProvider) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.numberOfFloors = numberOfFloors;
        this.name = name;
        this.contactPhone = contactPhone;
        this.description = description;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.bookingProvider = bookingProvider;
    }
}
