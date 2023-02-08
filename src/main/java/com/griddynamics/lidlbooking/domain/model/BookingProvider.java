package com.griddynamics.lidlbooking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingProvider {

    public static final int INT = 31;
    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookingProvider that = (BookingProvider) o;

        if (!Objects.equals(jmbg, that.jmbg)) {
            return false;
        }
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        int result = jmbg != null ? jmbg.hashCode() : 0;
        result = INT * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
