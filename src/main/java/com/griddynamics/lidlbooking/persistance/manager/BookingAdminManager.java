package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.BookingAdmin;
import com.griddynamics.lidlbooking.persistance.entity.BookingAdminEntity;
import com.griddynamics.lidlbooking.persistance.mapper.BookingAdminManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.BookingAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static liquibase.pro.packaged.lJ.getRootCause;

@Component
public class BookingAdminManager {

    private final BookingAdminRepository bookingAdminRepository;
    private final BookingAdminManagerMapper mapper;

    @Autowired
    public BookingAdminManager(final BookingAdminRepository bookingAdminRepository,
                               final BookingAdminManagerMapper mapper) {
        this.bookingAdminRepository = bookingAdminRepository;
        this.mapper = mapper;
    }

    public boolean existsByIdAndUsername(final Long id, final String username) {
        return bookingAdminRepository.existsByIdAndUsername(id, username);
    }

    public BookingAdmin findById(final Long id) {
        try {
            BookingAdminEntity adminEntity = bookingAdminRepository.findById(id).orElseThrow(
                    () -> new ElementNotFoundException(ErrorMessage.ID_INVALID_EXCEPTION
                            + ErrorMessage.formatVariable(id))
            );
            return mapper.bookingAdminEntityToBookingAdmin(adminEntity);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(ErrorMessage.ID_FORMAT_EXCEPTION);
        }
    }

    public BookingAdmin findByUsername(final String username) {
        BookingAdminEntity adminEntity;
        try {
            adminEntity = bookingAdminRepository.findByUsername(username).orElseThrow(
                    () -> new ElementNotFoundException(ErrorMessage.USERNAME_INVALID_EXCEPTION
                            + ErrorMessage.formatVariable(username)));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.USERNAME_FORMAT_EXCEPTION);
        }
        return mapper.bookingAdminEntityToBookingAdmin(adminEntity);
    }

    public void save(final BookingAdmin bookingAdmin) {
        try {
            BookingAdminEntity bookingAdminEntity = mapper.bookingAdminToBookingAdminEntity(bookingAdmin);
            bookingAdminRepository.save(bookingAdminEntity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.ADMIN_ERROR + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new SQLException(ErrorMessage.ADMIN_ERROR
                    + Objects.requireNonNull(getRootCause(e)).getMessage());
        }
    }
}
