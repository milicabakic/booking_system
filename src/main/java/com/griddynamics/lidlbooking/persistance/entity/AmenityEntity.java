package com.griddynamics.lidlbooking.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "amenities")
@Setter
@Getter
@NoArgsConstructor
public class AmenityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent")
    private AmenityEntity parent;


}
