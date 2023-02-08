package com.griddynamics.lidlbooking.persistance.repository;

import com.griddynamics.lidlbooking.persistance.entity.BookingEntity;
import com.griddynamics.lidlbooking.persistance.entity.StudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findAllByStudioId(Long studioId);

    List<BookingEntity> findAllByStudioAndBookingUserNotNull(StudioEntity studio);

    List<BookingEntity> findAllByStudioIdAndFromDateGreaterThanEqualAndToDateLessThanEqualAndBookingUserNotNull(
            Long studioId, LocalDate fromDate, LocalDate toDate);

    List<BookingEntity> findByStudioIdAndLengthOfStayAndBookingUserNotNull(Long studioId, int duration);

    List<BookingEntity> findByStudioIdAndFromDateAfterAndBookingUserNotNull(Long studioId, LocalDate date);

    List<BookingEntity> findByStudioIdAndToDateBeforeAndBookingUserNotNull(Long studioId, LocalDate date);

    List<BookingEntity> findAllByStudio(StudioEntity studioEntity);

}
