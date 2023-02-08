package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.persistance.entity.BookingUserEntity;
import com.griddynamics.lidlbooking.persistance.mapper.BookingUserManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.BookingUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.BOOKING_USER_ERROR;
import static liquibase.pro.packaged.lJ.getRootCause;

@Component
public class BookingUserManager {

    private final BookingUserRepository bookingUserRepository;
    private final BookingUserManagerMapper mapper;


    @Autowired
    public BookingUserManager(final BookingUserRepository userRepository,
                              final BookingUserManagerMapper mapper) {
        this.bookingUserRepository = userRepository;
        this.mapper = mapper;
    }

    public void save(final BookingUser bookingUser) {
        try {
            bookingUserRepository.save(mapper.bookingUserToBookingUserEntity(bookingUser));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(BOOKING_USER_ERROR + e.getMessage());
        } catch (
                DataIntegrityViolationException e) {
            throw new SQLException(BOOKING_USER_ERROR + Objects.requireNonNull(getRootCause(e)).getMessage());
        }
    }

    public boolean existsByUsername(final String username) {
        return bookingUserRepository.existsByUsername(username);
    }

    public BookingUser findByUsername(final String username) {
        try {
            BookingUserEntity bookingUserEntity = bookingUserRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ElementNotFoundException(ErrorMessage.USERNAME_INVALID_EXCEPTION
                            + ErrorMessage.formatVariable(username)));
            return mapper.bookingUserEntityToBookingUser(bookingUserEntity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.USERNAME_FORMAT_EXCEPTION);
        }
    }

    public boolean existsByIdAndUsername(final Long id, final String username) {
        return bookingUserRepository.existsByIdAndUsername(id, username);
    }

    public BookingUser findById(final Long id) {
        try {
            BookingUserEntity bookingUserEntity = bookingUserRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new ElementNotFoundException(ErrorMessage.ID_INVALID_EXCEPTION
                                    + ErrorMessage.formatVariable(id))
                    );
            return mapper.bookingUserEntityToBookingUser(bookingUserEntity);
        } catch (
                InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(ErrorMessage.ID_FORMAT_EXCEPTION);
        }
    }

    public List<BookingUser> findAll() {
        List<BookingUserEntity> userEntityList = bookingUserRepository.findAll();
        return mapper.bookingUserEntityListToBookingUserList(userEntityList);
    }
}
