package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.BookingAdmin;

public interface BookingAdminService {

    BookingAdmin editBookingAdmin(Long id, BookingAdmin bookingAdmin);

    void saveUpdateBookingAdmin(BookingAdmin admin);

    BookingAdmin findBookingAdminById(Long id);

    BookingAdmin findByUsername(String username);

}
