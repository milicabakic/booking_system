package com.griddynamics.lidlbooking.persistance.entity;

import com.griddynamics.lidlbooking.commons.BedType;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "studio")
@Getter
@Setter
@NoArgsConstructor
@TypeDefs({
        @TypeDef(
                typeClass = EnumArrayType.class,
                defaultForType = BedType[].class,
                parameters = {
                        @org.hibernate.annotations.Parameter(
                                name = EnumArrayType.SQL_ARRAY_TYPE,
                                value = "bedtype")
                }
        )
})
@ToString
public class StudioEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @Column(name = "beds",
            columnDefinition = "bedtype[]")
    private BedType[] beds;

    @ManyToOne
    @JoinColumn(name = "service_facility_id")
    private ServiceFacilityEntity serviceFacility;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "studio_amenities",
            joinColumns = {@JoinColumn(name = "studio_id")},
            inverseJoinColumns = {@JoinColumn(name = "amenities_id")}
    )
    private Set<AmenityEntity> amenities;

    @Column(name = "classification_type")
    private String classificationType;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "season_studio",
            joinColumns = {@JoinColumn(name = "studio_id")},
            inverseJoinColumns = {@JoinColumn(name = "season_id")}
    )
    private List<SeasonEntity> seasons;

    @ManyToOne
    @JoinColumn(name = "booking_provider_id")
    private BookingProviderEntity bookingProvider;

}
