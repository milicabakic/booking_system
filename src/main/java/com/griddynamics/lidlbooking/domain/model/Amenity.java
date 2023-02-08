package com.griddynamics.lidlbooking.domain.model;

import com.griddynamics.lidlbooking.domain.model.structure.AmenityStructure;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Amenity {

    private Long id;

    private String name;

    private AmenityStructure parent;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Amenity amenity = (Amenity) o;

        return Objects.equals(id, amenity.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
