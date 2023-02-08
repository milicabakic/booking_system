package com.griddynamics.lidlbooking.persistance.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "season")
@Data
@ToString
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "price_per_night")
    private int pricePerNight;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private BookingProviderEntity creator;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "season_studio",
            joinColumns = {@JoinColumn(name = "season_id")},
            inverseJoinColumns = {@JoinColumn(name = "studio_id")}
    )
    private List<StudioEntity> studios;

}
