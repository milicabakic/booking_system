package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.PasswordService;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.domain.model.BookingAdmin;
import com.griddynamics.lidlbooking.domain.service.BookingAdminService;
import com.griddynamics.lidlbooking.persistance.manager.BookingAdminManager;
import org.springframework.beans.factory.annotation.Autowired;


public class BookingAdminServiceImpl implements BookingAdminService {

    private final BookingAdminManager bookingAdminManager;
    private final PasswordService passwordConfiguration;
    private final JWTService jwtService;

    @Autowired
    public BookingAdminServiceImpl(final BookingAdminManager bookingAdminManager,
                                   final PasswordService passwordConfiguration, final JWTService jwtService) {
        this.bookingAdminManager = bookingAdminManager;
        this.passwordConfiguration = passwordConfiguration;
        this.jwtService = jwtService;
    }

    public BookingAdmin editBookingAdmin(final Long id, final BookingAdmin bookingAdmin) {
        validateAdminId(id);
        BookingAdmin admin = findBookingAdminById(id);
        editBookingAdminAttributes(admin, bookingAdmin);
        saveUpdateBookingAdmin(admin);
        return admin;
    }

    public void saveUpdateBookingAdmin(final BookingAdmin admin) {
        bookingAdminManager.save(admin);
    }

    public BookingAdmin findBookingAdminById(final Long id) {
        return bookingAdminManager.findById(id);
    }

    private void editBookingAdminAttributes(final BookingAdmin admin, final BookingAdmin post) {
        admin.setUsername(post.getUsername());
        admin.setJmbg(post.getJmbg());
        admin.setPassword(passwordConfiguration.encode(post.getPassword()));
        admin.setFirstName(post.getFirstName());
        admin.setLastName(post.getLastName());
        admin.setEmail(post.getEmail());
        admin.setPhoneNumber(post.getPhoneNumber());
    }

    private void validateAdminId(final Long id) {
        if (!checkMatching(id, jwtService.whoAmI())) {
            throw new BadRequestException(ErrorMessage.ACCESS_DENIED);
        }
    }

    public BookingAdmin findByUsername(final String username) {
        return bookingAdminManager.findByUsername(username);
    }

    private boolean checkMatching(final BookingAdmin admin, final Long id) {
        return admin.getId() == id;
    }

    private boolean checkMatching(final Long id, final String username) {
        return bookingAdminManager.existsByIdAndUsername(id, username);
    }
}
