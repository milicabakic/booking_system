package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.users.BookingProviderDto;
import com.griddynamics.lidlbooking.api.dto.users.EditUserWithJmbgFormDTO;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingProviderControllerMapper {

    BookingProvider editUserFormToBookingProvider(EditUserWithJmbgFormDTO form);

    BookingProviderDto bookingProviderToBookingProviderDto(BookingProvider bookingProvider);
}
