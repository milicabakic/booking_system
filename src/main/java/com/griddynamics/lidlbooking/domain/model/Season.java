package com.griddynamics.lidlbooking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Season {

    private Long id;
    private String name;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int pricePerNight;
    private BookingProvider creator;

    @Override
    public String toString() {
        return "Season{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Season season = (Season) o;
        return Objects.equals(id, season.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
