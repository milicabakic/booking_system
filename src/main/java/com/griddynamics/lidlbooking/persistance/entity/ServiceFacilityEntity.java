package com.griddynamics.lidlbooking.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetTime;

@Entity
@Table(name = "service_facility")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ServiceFacilityEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "number_of_floors")
    private Integer numberOfFloors;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "description")
    private String description;

    @Column(name = "checkin_time")
    private OffsetTime checkinTime;

    @Column(name = "checkout_time")
    private OffsetTime checkoutTime;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @ManyToOne
    @JoinColumn(name = "country_iso3")
    private CountryEntity country;
    @ManyToOne
    @JoinColumn(name = "booking_provider_id")
    private BookingProviderEntity bookingProvider;

    public ServiceFacilityEntity(final String address, final CityEntity city, final CountryEntity country,
                                 final String postalCode, final Integer numberOfFloors, final String name,
                                 final String contactPhone, final String description, final OffsetTime checkinTime,
                                 final OffsetTime checkoutTime, final BookingProviderEntity bookingProvider) {
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
