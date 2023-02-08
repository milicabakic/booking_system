package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.users.BookingProviderDto;
import com.griddynamics.lidlbooking.api.dto.users.BookingUserDto;
import com.griddynamics.lidlbooking.api.dto.users.ProviderRegistrationFormDTO;
import com.griddynamics.lidlbooking.api.dto.users.UserRegistrationFormDTO;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthControllerMapper {

    BookingProvider providerRegistrationFormToBookingProvider(ProviderRegistrationFormDTO form);

    BookingUser userRegistrationFormToBookingUser(UserRegistrationFormDTO form);

    BookingUserDto bookingUserToBookingUserDto(BookingUser user);

    BookingProviderDto bookingProviderToBookingProviderDto(BookingProvider provider);
}
