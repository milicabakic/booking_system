package com.griddynamics.lidlbooking.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
@Setter
@Getter
@NoArgsConstructor
public class CountryEntity {

    @Id
    @Column(name = "iso3")
    private String iso3;

    @Column(name = "name")
    private String name;


    public CountryEntity(final String iso3, final String name) {
        this.iso3 = iso3;
        this.name = name;
    }

}
