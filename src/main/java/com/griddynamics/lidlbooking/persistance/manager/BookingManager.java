package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.Booking;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.persistance.entity.BookingEntity;
import com.griddynamics.lidlbooking.persistance.entity.StudioEntity;
import com.griddynamics.lidlbooking.persistance.mapper.BookingManagerMapper;
import com.griddynamics.lidlbooking.persistance.mapper.StudioManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.springframework.core.NestedExceptionUtils.getRootCause;

@Component
public class BookingManager {

    private final BookingManagerMapper mapper;
    private final BookingRepository bookingRepository;
    private final StudioManagerMapper studioMapper;


    @Autowired
    public BookingManager(final BookingManagerMapper mapper, final BookingRepository bookingRepository,
                          final StudioManagerMapper studioMapper) {
        this.mapper = mapper;
        this.bookingRepository = bookingRepository;
        this.studioMapper = studioMapper;
    }

    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        List<BookingEntity> bookingEntityList = bookingRepository.findAll();
        return mapper.bookingEntityListToBookingList(bookingEntityList);
    }

    @Transactional(readOnly = true)
    public List<Booking> findAllByStudio(final Studio studio) {
        if (studio == null) {
            throw new BadRequestException(ErrorMessage.BOOKING_VALIDATION);
        }
        StudioEntity studioEntity = studioMapper.studioToStudioEntity(studio);
        List<BookingEntity> bookingEntityList = bookingRepository.findAllByStudio(studioEntity);
        return mapper.bookingEntityListToBookingList(bookingEntityList);
    }

    @Transactional(readOnly = true)
    public List<Booking> findAllByStudioId(final Long studioId) {
        if (studioId == null) {
            throw new BadRequestException(ErrorMessage.studioIdDoesNotExist(studioId));
        }
        List<BookingEntity> bookingEntityList = bookingRepository.findAllByStudioId(studioId);
        return mapper.bookingEntityListToBookingList(bookingEntityList);
    }

    @Transactional(readOnly = true)
    public List<Booking> findAllByStudio(final Long studioId, final LocalDate startDate, final LocalDate endDate) {
        List<BookingEntity> bookingEntityList = bookingRepository.
                findAllByStudioIdAndFromDateGreaterThanEqualAndToDateLessThanEqualAndBookingUserNotNull(
                        studioId, startDate, endDate);
        return mapper.bookingEntityListToBookingList(bookingEntityList);
    }

    @Transactional
    public Booking save(final Booking booking) {
        try {
            BookingEntity entity = mapper.bookingToBookingEntity(booking);
            entity = bookingRepository.save(entity);
            return mapper.bookingEntityToBooking(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.BOOKING_ERROR + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new SQLException(ErrorMessage.BOOKING_ERROR + Objects.requireNonNull(getRootCause(e)).getMessage());
        }
    }


    @Transactional
    public List<Booking> findAllByStudio(final Long studioId, final int lengthOfStay) {
        List<BookingEntity> bookingEntityList = bookingRepository.findByStudioIdAndLengthOfStayAndBookingUserNotNull(
                studioId, lengthOfStay);
        return  mapper.bookingEntityListToBookingList(bookingEntityList);
    }

    @Transactional
    public List<Booking> findAllByStudioAfterDate(final Long studioId, final LocalDate afterDate) {
        List<BookingEntity> bookingEntityList = bookingRepository.findByStudioIdAndFromDateAfterAndBookingUserNotNull(
                studioId, afterDate);
        return  mapper.bookingEntityListToBookingList(bookingEntityList);
    }

    @Transactional
    public List<Booking> findAllByStudio(final Long studioId, final LocalDate endDate) {
        List<BookingEntity> bookingEntityList = bookingRepository.findByStudioIdAndToDateBeforeAndBookingUserNotNull(
                studioId, endDate);
        return  mapper.bookingEntityListToBookingList(bookingEntityList);
    }

}
