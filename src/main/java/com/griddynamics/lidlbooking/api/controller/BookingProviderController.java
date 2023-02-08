package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.users.BookingProviderDto;
import com.griddynamics.lidlbooking.api.dto.users.EditUserWithJmbgFormDTO;
import com.griddynamics.lidlbooking.api.mapper.BookingProviderControllerMapper;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/provider")
@Tag(name = "BookingProvider API")
public class BookingProviderController {

    private final BookingProviderService bookingProviderService;
    private final BookingProviderControllerMapper mapper;
    private final String mediaType = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    public BookingProviderController(final BookingProviderService bookingProviderService,
                                     final BookingProviderControllerMapper mapper) {
        this.bookingProviderService = bookingProviderService;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{username}", produces = mediaType)
    @Operation(summary = "Find booking provider", description = "Find booking provider by username")
    public BookingProviderDto findProvider(@PathVariable("username") @Parameter(name = "username",
            example = "pera") final String username) {
        BookingProvider bookingProvider = bookingProviderService.findBookingProviderByUsername(username);
        return mapper.bookingProviderToBookingProviderDto(bookingProvider);
    }

    @PutMapping(path = "/{id}", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Edit booking provider", description = "Edit booking provider's data. "
            + "Each booking provider can only edit its own data.")
    public BookingProviderDto editProvider(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id, @RequestBody final EditUserWithJmbgFormDTO form) {
        BookingProvider bookingProvider = mapper.editUserFormToBookingProvider(form);
        bookingProviderService.editBookingProvider(id, bookingProvider);
        return mapper.bookingProviderToBookingProviderDto(bookingProvider);
    }
}
