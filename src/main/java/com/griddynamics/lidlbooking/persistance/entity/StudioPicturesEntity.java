package com.griddynamics.lidlbooking.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "studio_pictures")
@Setter
@Getter
@NoArgsConstructor
public class StudioPicturesEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "picture")
    private byte[] picture;

    @ManyToOne()
    @JoinColumn(name = "studio_id")
    private StudioEntity studio;
}
