package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.persistance.entity.BookingUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingUserManagerMapper {

    BookingUserEntity bookingUserToBookingUserEntity(BookingUser bookingUser);

    BookingUser bookingUserEntityToBookingUser(BookingUserEntity bookingUserEntity);

    List<BookingUser> bookingUserEntityListToBookingUserList(List<BookingUserEntity> bookingUserEntityList);
}
