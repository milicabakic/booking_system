package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.BookingDto;
import com.griddynamics.lidlbooking.domain.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingControllerMapper {

    BookingDto bookingToBookingDto(Booking booking);

    Booking bookingDtoToBooking(BookingDto bookingDto);

    List<BookingDto> bookingListToBookingDtoList(List<Booking> bookingList);

}
