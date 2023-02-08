package com.griddynamics.lidlbooking.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "facility_pictures")
@Setter
@Getter
@NoArgsConstructor
public class FacilityPicturesEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "picture")
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "service_facility_id")
    private ServiceFacilityEntity facility;
}
