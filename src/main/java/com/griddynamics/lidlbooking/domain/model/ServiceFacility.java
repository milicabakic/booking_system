package com.griddynamics.lidlbooking.domain.model;

import com.griddynamics.lidlbooking.domain.model.structure.BookingProviderStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CityStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CountryStructure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFacility {

    private Long id;

    private String address;

    private CityStructure city;

    private CountryStructure country;

    private String postalCode;

    private Integer numberOfFloors;

    private String name;

    private String contactPhone;

    private String description;

    private OffsetTime checkinTime;

    private OffsetTime checkoutTime;

    private BookingProviderStructure bookingProvider;


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceFacility that = (ServiceFacility) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}


