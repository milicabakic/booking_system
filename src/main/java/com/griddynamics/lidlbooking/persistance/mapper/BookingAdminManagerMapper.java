package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.BookingAdmin;
import com.griddynamics.lidlbooking.persistance.entity.BookingAdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingAdminManagerMapper {

    BookingAdmin bookingAdminEntityToBookingAdmin(BookingAdminEntity bookingAdminEntity);

    BookingAdminEntity bookingAdminToBookingAdminEntity(BookingAdmin bookingAdmin);
}
