package com.griddynamics.lidlbooking.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "city")
@Setter
@Getter
@NoArgsConstructor
public class CityEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "province")
    private String province;

    @ManyToOne
    @JoinColumn(name = "country_iso3")
    private CountryEntity country;

    public CityEntity(final String name, final String province, final CountryEntity country) {
        this.name = name;
        this.province = province;
        this.country = country;
    }
}
