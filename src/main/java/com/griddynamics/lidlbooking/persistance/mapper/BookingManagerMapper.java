package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.Booking;
import com.griddynamics.lidlbooking.persistance.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingManagerMapper {

    Booking bookingEntityToBooking(BookingEntity bookingEntity);

    BookingEntity bookingToBookingEntity(Booking booking);

    List<Booking> bookingEntityListToBookingList(List<BookingEntity> bookingEntityList);

    List<BookingEntity> bookingListToBookingEntityList(List<Booking> bookingList);

}
