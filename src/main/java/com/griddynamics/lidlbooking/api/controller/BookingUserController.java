package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.users.BookingUserDto;
import com.griddynamics.lidlbooking.api.dto.users.EditUserFormDTO;
import com.griddynamics.lidlbooking.api.mapper.BookingUserControllerMapper;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.domain.service.BookingUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
@Tag(name = "BookingUser API")
public class BookingUserController {

    private final String mediaType = MediaType.APPLICATION_JSON_VALUE;
    private final BookingUserService bookingUserService;
    private final BookingUserControllerMapper mapper;

    @Autowired
    public BookingUserController(final BookingUserService bookingUserService,
                                 final BookingUserControllerMapper mapper) {
        this.bookingUserService = bookingUserService;
        this.mapper = mapper;
    }

    @GetMapping(produces = mediaType)
    public List<BookingUser> findAll() {
        return bookingUserService.findAll();
    }

    @GetMapping(path = "/{username}", produces = mediaType)
    @Operation(summary = "Find booking user", description = "Find a booking user with provided username")
    public BookingUserDto findUser(@PathVariable("username") @Parameter(name = "username",
            example = "iivanovic") final String username) {
        BookingUser bookingUser = bookingUserService.findBookingUserByUsername(username);
        return mapper.bookingUserToBookingUserDto(bookingUser);
    }

    @PutMapping(path = "/{id}", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Edit booking user", description = "Edit booking user's data.")
    public BookingUserDto editUser(@PathVariable final Long id, @RequestBody final EditUserFormDTO form) {
        BookingUser userToEdit = mapper.editUserFormToBookingUser(form);
        bookingUserService.editBookingUser(id, userToEdit);
        return mapper.bookingUserToBookingUserDto(userToEdit);
    }
}
