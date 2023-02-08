package com.griddynamics.lidlbooking.persistance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Setter
@Getter
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    private String note;

    @ManyToOne
    @JoinColumn(name = "booking_user_id")
    private BookingUserEntity bookingUser;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private StudioEntity studio;

    @Column(name = "length_of_stay")
    private int lengthOfStay;

}
