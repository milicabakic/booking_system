package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.domain.model.Booking;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.domain.model.Season;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.service.BookingService;
import com.griddynamics.lidlbooking.domain.service.BookingUserService;
import com.griddynamics.lidlbooking.domain.service.SeasonService;
import com.griddynamics.lidlbooking.domain.service.StudioService;
import com.griddynamics.lidlbooking.persistance.manager.BookingManager;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class BookingServiceImpl implements BookingService {

    private final BookingManager manager;
    private final StudioService studioService;
    private JWTService jwtService;
    private final BookingUserService bookingUserService;
    private final SeasonService seasonService;


    public BookingServiceImpl(final BookingManager manager, final StudioService studioService,
                              final JWTService jwtService, final BookingUserService bookingUserService,
                              final SeasonService seasonService) {
        this.manager = manager;
        this.studioService = studioService;
        this.jwtService = jwtService;
        this.bookingUserService = bookingUserService;
        this.seasonService = seasonService;
    }

    @Override
    public List<Booking> findAll() {
        return manager.findAll();
    }

    @Override
    public List<Booking> findAllByStudioId(final Long studioId) {
        return manager.findAllByStudioId(studioId);
    }

    @Override
    public List<Booking> findAllByStudio(final Studio studio) {
        return manager.findAllByStudio(studio);
    }

    @Override
    public Booking bookStudio(final Long studioId, final Booking booking) {
        Studio studio = studioService.findById(studioId);
        validateBooking(booking);
        checkDateRange(booking.getFromDate(), booking.getToDate(), studio.getSeasons());
        checkStudioAvailability(booking, studio);
        booking.setStudio(studio);
        booking.setBookingUser(getBookingUser());
        booking.setLengthOfStay((int) DAYS.between(booking.getFromDate(), booking.getToDate()));
        return save(booking);
    }

    @Override
    public List<Booking> searchByStudio(final Long studioId, final List<Long> seasonIdList,
                                        final LocalDate startDate, final LocalDate endDate) {
        validateSearch(studioId, seasonIdList);
        List<Booking> bookings = manager.findAllByStudio(studioId, startDate, endDate);
        return bookings;
    }

    @Override
    public List<Booking> searchByStudio(final Long studioId, final List<Long> seasonIdList, final int lengthOfStay) {
        validateSearch(studioId, seasonIdList);
        List<Booking> bookings = manager.findAllByStudio(studioId, lengthOfStay);
        return bookings;
    }

    @Override
    public List<Booking> searchByStudioAfter(final Long studioId, final List<Long> seasonIdList, final LocalDate afterDate) {
        validateSearch(studioId, seasonIdList);
        List<Booking> bookings = manager.findAllByStudioAfterDate(studioId, afterDate);
        return bookings;
    }

    @Override
    public List<Booking> searchByStudio(final Long studioId, final List<Long> seasonIdList, final LocalDate endDate) {
        validateSearch(studioId, seasonIdList);
        List<Booking> bookings = manager.findAllByStudio(studioId, endDate);
        return bookings;
    }

    @Override
    public Booking disableStudioForBooking(final Long studioId, final Booking booking) {
        Studio studio = studioService.findById(studioId);
        validateBooking(booking);
        checkStudioAvailability(booking, studio);
        booking.setStudio(studio);
        booking.setLengthOfStay((int) DAYS.between(booking.getFromDate(), booking.getToDate()));
        return save(booking);
    }

    private Booking save(final Booking booking) {
        return manager.save(booking);
    }

    private BookingUser getBookingUser() {
        String username = jwtService.whoAmI();
        return bookingUserService.findBookingUserByUsername(username);
    }

    private void checkStudioAvailability(final Booking bookingOnWait, final Studio studio) {
        for (Booking booking : findAllByStudio(studio)) {
            checkOverlapping(booking, bookingOnWait);
        }
    }

    private void checkOverlapping(final Booking booking, final Booking bookingOnWait) {
        if (isOverlapping(booking.getFromDate(), booking.getToDate(),
                bookingOnWait.getFromDate(), bookingOnWait.getToDate())) {
            throw new BadRequestException(ErrorMessage.BOOKING_STUDIO);
        }
    }

    private void validateSearch(final Long studioId, final List<Long> seasonIdList) {
        if (seasonIdList == null || seasonIdList.isEmpty()) {
            return;
        }
        validateSeasons(seasonIdList);
        validateSeasonsAndStudio(studioId, seasonIdList);
    }

    private void validateSeasons(final List<Long> seasonIdList) {
        if (!seasonService.existsById(seasonIdList)) {
            throw new BadRequestException(ErrorMessage.SEASONS_INVALID);
        }
    }

    private void validateSeasonsAndStudio(final Long studioId, final List<Long> seasonIdList) {
        if (!seasonService.checkSeasonsStudioMatch(studioId, seasonIdList)) {
            throw new BadRequestException(ErrorMessage.SEASON_STUDIO_MISMATCH);
        }
    }

    private void validateBooking(final Booking booking) {
        if (booking == null) {
            throw new BadRequestException(ErrorMessage.BOOKING_VALIDATION);
        }
    }

    private boolean isOverlapping(final LocalDate start1, final LocalDate end1,
                                  final LocalDate start2, final LocalDate end2) {
        return start1.compareTo(end2) <= 0 && end1.compareTo(start2) >= 0;
    }

    private void checkDateRange(final LocalDate fromDate, final LocalDate toDate, final List<Season> seasons) {
        for (Season season : seasons) {
            if (isOverlapping(fromDate, toDate, season.getFromDate(), season.getToDate())) {
                return;
            }
        }
        throw new BadRequestException(ErrorMessage.BOOKING_SEASON);
    }

}
