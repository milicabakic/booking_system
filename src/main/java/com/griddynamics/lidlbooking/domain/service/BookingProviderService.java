package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.BookingProvider;

public interface BookingProviderService {

    BookingProvider create(BookingProvider bookingProvider);

    BookingProvider editBookingProvider(Long id, BookingProvider providerEdited);

    void saveUpdateBookingProvider(BookingProvider provider);

    BookingProvider findBookingProviderByUsername(String username);

    BookingProvider findBookingProviderById(Long id);

}
