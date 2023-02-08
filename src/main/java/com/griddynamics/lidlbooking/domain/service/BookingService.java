package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.Booking;
import com.griddynamics.lidlbooking.domain.model.Studio;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<Booking> findAll();

    List<Booking> findAllByStudioId(Long studioId);

    List<Booking> findAllByStudio(Studio studio);

    Booking bookStudio(Long studioId, Booking booking);

    List<Booking> searchByStudio(Long studioId, List<Long> seasonIdList,
                                 LocalDate startDate, LocalDate endDate);

    List<Booking> searchByStudio(Long studioId, List<Long> seasonIdList,
                                 int lengthOfStay);

    List<Booking> searchByStudioAfter(Long studioId, List<Long> seasonIdList, LocalDate afterDate);

    List<Booking> searchByStudio(Long studioId, List<Long> seasonIdList, LocalDate endDate);

    Booking disableStudioForBooking(Long studioId, Booking booking);

}
