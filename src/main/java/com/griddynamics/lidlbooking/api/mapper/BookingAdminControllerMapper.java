package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.users.BookingAdminDto;
import com.griddynamics.lidlbooking.api.dto.users.EditUserWithJmbgFormDTO;
import com.griddynamics.lidlbooking.domain.model.BookingAdmin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingAdminControllerMapper {

    BookingAdmin editUserFormToBookingAdmin(EditUserWithJmbgFormDTO form);

    BookingAdminDto bookingAdminToBookingAdminDto(BookingAdmin bookingAdmin);
}
