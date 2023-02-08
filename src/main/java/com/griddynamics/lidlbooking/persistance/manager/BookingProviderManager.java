package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.persistance.entity.BookingProviderEntity;
import com.griddynamics.lidlbooking.persistance.mapper.BookingProviderManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.BookingProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static liquibase.pro.packaged.lJ.getRootCause;


@Component
public class BookingProviderManager {

    private final BookingProviderRepository bookingProviderRepository;
    private final BookingProviderManagerMapper mapper;


    @Autowired
    public BookingProviderManager(final BookingProviderRepository bookingProviderRepository,
                                  final BookingProviderManagerMapper mapper) {
        this.bookingProviderRepository = bookingProviderRepository;
        this.mapper = mapper;
    }

    public void save(final BookingProvider bookingProvider) {
        try {
            bookingProviderRepository.save(mapper.bookingProviderToBookingProviderEntity(bookingProvider));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.BOOKING_PROVIDER_ERROR + e.getMessage());
        } catch (
                DataIntegrityViolationException e) {
            throw new SQLException(ErrorMessage.BOOKING_PROVIDER_ERROR
                    + Objects.requireNonNull(getRootCause(e)).getMessage());
        }
    }

    public boolean existsByUsername(final String username) {
        return bookingProviderRepository.existsByUsername(username);
    }

    public BookingProvider findByUsername(final String username) {
        try {
            BookingProviderEntity providerEntity = bookingProviderRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ElementNotFoundException(ErrorMessage.USERNAME_INVALID_EXCEPTION
                            + ErrorMessage.formatVariable(username)));
            return mapper.bookingProviderEntityToBookingProvider(providerEntity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.USERNAME_FORMAT_EXCEPTION);
        }
    }

    public boolean existsByIdAndUsername(final Long id, final String username) {
        return bookingProviderRepository.existsByIdAndUsername(id, username);
    }

    public BookingProvider findById(final Long id) {
        try {
            BookingProviderEntity bookingProviderEntity = bookingProviderRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new ElementNotFoundException(ErrorMessage.ID_INVALID_EXCEPTION
                                    + ErrorMessage.formatVariable(id))
                    );
            return mapper.bookingProviderEntityToBookingProvider(bookingProviderEntity);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(ErrorMessage.ID_FORMAT_EXCEPTION);
        }
    }
}
