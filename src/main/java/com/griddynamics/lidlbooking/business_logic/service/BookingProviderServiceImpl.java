package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.PasswordService;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
import com.griddynamics.lidlbooking.persistance.manager.BookingProviderManager;
import org.springframework.beans.factory.annotation.Autowired;


public class BookingProviderServiceImpl implements BookingProviderService {

    private final BookingProviderManager bookingProviderManager;
    private final PasswordService passwordConfiguration;
    private final JWTService jwtService;

    @Autowired
    public BookingProviderServiceImpl(final BookingProviderManager bookingProviderManager,
                                      final PasswordService passwordConfiguration,
                                      final JWTService jwtService) {
        this.bookingProviderManager = bookingProviderManager;
        this.passwordConfiguration = passwordConfiguration;
        this.jwtService = jwtService;
    }


    public BookingProvider create(final BookingProvider bookingProvider) {
        if (checkIfProviderExists(bookingProvider.getUsername())) {
            throw new RuntimeException();
        }
        saveUpdateBookingProvider(bookingProvider);
        return bookingProvider;
    }

    public boolean checkIfProviderExists(final String username) {
        return bookingProviderManager.existsByUsername(username);
    }

    public BookingProvider findBookingProviderByUsername(final String username) {
        return bookingProviderManager.findByUsername(username);
    }

    public BookingProvider editBookingProvider(final Long id, final BookingProvider providerEdited) {
        validateProviderId(id);
        BookingProvider provider = findBookingProviderById(id);
        editBookingProviderAttributes(provider, providerEdited);
        saveUpdateBookingProvider(provider);
        return provider;
    }

    public void saveUpdateBookingProvider(final BookingProvider provider) {
        provider.setPassword(encodePassword(provider.getPassword()));
        bookingProviderManager.save(provider);
    }

    public BookingProvider findBookingProviderById(final Long id) {
        return bookingProviderManager.findById(id);
    }

    private String encodePassword(final String password) {
        return passwordConfiguration.encode(password);
    }

    private void validateProviderId(final Long id) {
        if (!checkMatching(id, jwtService.whoAmI())) {
            throw new BadRequestException(ErrorMessage.ACCESS_DENIED);
        }
    }

    private void editBookingProviderAttributes(final BookingProvider provider,
                                               final BookingProvider providerEdited) {
        provider.setUsername(providerEdited.getUsername());
        provider.setPassword(passwordConfiguration.encode(providerEdited.getPassword()));
        provider.setJmbg(providerEdited.getJmbg());
        provider.setFirstName(providerEdited.getFirstName());
        provider.setLastName(providerEdited.getLastName());
        provider.setEmail(providerEdited.getEmail());
        provider.setPhoneNumber(providerEdited.getPhoneNumber());
    }

    private boolean checkMatching(final BookingProvider provider, final Long id) {
        return provider.getId() == id;
    }

    private boolean checkMatching(final Long id, final String username) {
        return bookingProviderManager.existsByIdAndUsername(id, username);
    }
}
