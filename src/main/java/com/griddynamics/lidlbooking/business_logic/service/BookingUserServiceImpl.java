package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.PasswordService;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.domain.service.BookingUserService;
import com.griddynamics.lidlbooking.persistance.manager.BookingUserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookingUserServiceImpl implements BookingUserService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingUserServiceImpl.class);
    private final BookingUserManager bookingUserManager;
    private final PasswordService passwordConfig;
    private final JWTService jwtService;

    @Autowired
    public BookingUserServiceImpl(final BookingUserManager bookingUserManager,
                                  final PasswordService passwordConfig,
                                  final JWTService jwtService) {
        this.bookingUserManager = bookingUserManager;
        this.passwordConfig = passwordConfig;
        this.jwtService = jwtService;
    }

    public BookingUser create(final BookingUser bookingUser) {
        saveUpdateUser(bookingUser);
        return bookingUser;
    }

    public List<BookingUser> findAll() {
        return bookingUserManager.findAll();
    }

    public BookingUser findBookingUserByUsername(final String username) {
        return bookingUserManager.findByUsername(username);
    }

    public BookingUser findBookingUserById(final Long id) {
        return bookingUserManager.findById(id);
    }

    public boolean checkIfUserExists(final String username) {
        return bookingUserManager.existsByUsername(username);
    }

    public BookingUser editBookingUser(final Long id, final BookingUser userEdited) {
        validateUserId(id);
        BookingUser user = findBookingUserById(id);
        editBookingUserAttributes(user, userEdited);
        saveUpdateUser(user);
        return user;
    }

    public void saveUpdateUser(final BookingUser user) {
        user.setPassword(encodePassword(user.getPassword()));
        bookingUserManager.save(user);
    }

    private String encodePassword(final String password) {
        return passwordConfig.encode(password);
    }

    private void validateUserId(final Long id) {
        if (!checkMatching(id, jwtService.whoAmI())) {
            throw new RuntimeException();
        }
    }

    private void editBookingUserAttributes(final BookingUser user, final BookingUser userEdited) {
        user.setUsername(userEdited.getUsername());
        user.setPassword(passwordConfig.encode(userEdited.getPassword()));
        user.setFirstName(userEdited.getFirstName());
        user.setLastName(userEdited.getLastName());
        user.setEmail(userEdited.getEmail());
        user.setPhoneNumber(userEdited.getPhoneNumber());
    }

    private boolean checkMatching(final Long id, final String username) {
        return bookingUserManager.existsByIdAndUsername(id, username);
    }
}
