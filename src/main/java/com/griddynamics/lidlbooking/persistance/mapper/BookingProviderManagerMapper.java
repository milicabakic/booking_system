package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.persistance.entity.BookingProviderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingProviderManagerMapper {

    BookingProvider bookingProviderEntityToBookingProvider(BookingProviderEntity bookingProviderEntity);
    BookingProviderEntity bookingProviderToBookingProviderEntity(BookingProvider bookingProvider);
}
