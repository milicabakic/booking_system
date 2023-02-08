package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.BookingUser;

import java.util.List;

public interface BookingUserService {

    BookingUser create(BookingUser bookingUser);

    BookingUser editBookingUser(Long id, BookingUser userEdited);

    void saveUpdateUser(BookingUser user);

    List<BookingUser> findAll();

    BookingUser findBookingUserByUsername(String username);

    BookingUser findBookingUserById(Long id);
}
