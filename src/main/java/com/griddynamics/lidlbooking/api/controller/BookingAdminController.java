package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.users.BookingAdminDto;
import com.griddynamics.lidlbooking.api.dto.users.EditUserWithJmbgFormDTO;
import com.griddynamics.lidlbooking.api.mapper.BookingAdminControllerMapper;
import com.griddynamics.lidlbooking.domain.model.BookingAdmin;
import com.griddynamics.lidlbooking.domain.service.BookingAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/admin")
@Tag(name = "BookingAdmin API")
public class BookingAdminController {

    private final BookingAdminService bookingAdminService;
    private final BookingAdminControllerMapper mapper;
    private final String mediaType = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    public BookingAdminController(final BookingAdminService bookingAdminService,
                                  final BookingAdminControllerMapper mapper) {
        this.bookingAdminService = bookingAdminService;
        this.mapper = mapper;
    }

    @PutMapping(path = "/{id}", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Edit booking admin", description = "Edit booking admin's data")
    public BookingAdminDto editAdmin(@PathVariable final Long id, @RequestBody final EditUserWithJmbgFormDTO form) {
        BookingAdmin adminToEdit = mapper.editUserFormToBookingAdmin(form);
        bookingAdminService.editBookingAdmin(id, adminToEdit);
        return mapper.bookingAdminToBookingAdminDto(adminToEdit);
    }
}
