package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.users.BookingUserDto;
import com.griddynamics.lidlbooking.api.dto.users.EditUserFormDTO;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingUserControllerMapper {

    BookingUser editUserFormToBookingUser(EditUserFormDTO form);

    BookingUserDto bookingUserToBookingUserDto(BookingUser userToEdit);
}
