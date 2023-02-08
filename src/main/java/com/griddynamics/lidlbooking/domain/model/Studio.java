package com.griddynamics.lidlbooking.domain.model;

import com.griddynamics.lidlbooking.commons.BedType;
import com.griddynamics.lidlbooking.domain.model.structure.ServiceFacilityStructure;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Studio {

    private Long id;
    private String name;
    private Integer numberOfRooms;
    private Integer numberOfPeople;
    private BedType[] beds;
    private ServiceFacilityStructure serviceFacility;
    private BookingProvider bookingProvider;
    private List<Season> seasons;


    public Studio() {
        seasons = new ArrayList<>();
    }

    public Studio(final Long id) {
        this.id = id;
        seasons = new ArrayList<>();
    }

    public Studio(final long id, final String name, final int numberOfRooms, final int numberOfPeople,
                  final BedType[] beds, final ServiceFacilityStructure serviceFacilityStructure) {
        this.id = id;
        this.name = name;
        this.numberOfRooms = numberOfRooms;
        this.numberOfPeople = numberOfPeople;
        this.beds = beds;
        this.serviceFacility = serviceFacilityStructure;
    }

    private String classificationType;

    private Set<Amenity> amenities;

    public Studio(final Long id, final String name, final int numberOfRooms, final int numberOfPeople,
                  final BedType[] bedTypes, final ServiceFacilityStructure serviceFacilityStructure) {
        this.id = id;
        this.name = name;
        this.numberOfRooms = numberOfRooms;
        this.numberOfPeople = numberOfPeople;
        this.beds = bedTypes;
        this.serviceFacility = serviceFacilityStructure;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Studio studio = (Studio) o;
        return Objects.equals(id, studio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Studio{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
