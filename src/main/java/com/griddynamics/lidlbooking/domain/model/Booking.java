package com.griddynamics.lidlbooking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String note;
    private Studio studio;
    private BookingUser bookingUser;
    private int lengthOfStay;


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
