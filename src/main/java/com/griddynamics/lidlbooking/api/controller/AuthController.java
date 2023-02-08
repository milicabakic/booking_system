package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.users.*;
import com.griddynamics.lidlbooking.api.mapper.AuthControllerMapper;
import com.griddynamics.lidlbooking.api.dto.users.ProviderRegistrationFormDTO;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
import com.griddynamics.lidlbooking.domain.service.BookingUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/auth")
@Tag(name = "Authentication API")
public class AuthController {

    private BookingUserService userService;
    private BookingProviderService providerService;
    private AuthControllerMapper mapper;
    private final String mediaType = MediaType.APPLICATION_JSON_VALUE;

    @Autowired
    public AuthController(final BookingUserService userService, final BookingProviderService providerService,
                          final AuthControllerMapper mapper) {
        this.userService = userService;
        this.providerService = providerService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/provider/login", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Login booking provider", description = "Login booking provider with valid credentials")
    public ResponseEntity<?> providerLogin(@RequestBody final LoginFormDTO request) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/user/login", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Login user", description = "Login user with valid credentials")
    public ResponseEntity<?> userLogin(@RequestBody final LoginFormDTO request) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/admin/login", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Login booking admin", description = "Login booking admin with valid credentials")
    public ResponseEntity<?> adminLogin(@RequestBody final LoginFormDTO request) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/user/register", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Register booking user", description = "Register booking user with parameters")
    public BookingUserDto register(@RequestBody final UserRegistrationFormDTO request) {
        BookingUser user = mapper.userRegistrationFormToBookingUser(request);
        user = userService.create(user);
        return mapper.bookingUserToBookingUserDto(user);
    }

    @PostMapping(path = "/provider/register", consumes = mediaType, produces = mediaType)
    @Operation(summary = "Register booking provider", description = "Register booking provider with parameters")
    public BookingProviderDto register(@RequestBody final ProviderRegistrationFormDTO request) {
        BookingProvider provider = mapper.providerRegistrationFormToBookingProvider(request);
        provider = providerService.create(provider);
        return mapper.bookingProviderToBookingProviderDto(provider);
    }
}
