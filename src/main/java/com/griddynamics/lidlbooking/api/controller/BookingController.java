package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.BookingDto;
import com.griddynamics.lidlbooking.api.dto.StudioDTO;
import com.griddynamics.lidlbooking.api.dto.search.SearchBookingDto;
import com.griddynamics.lidlbooking.api.mapper.BookingControllerMapper;
import com.griddynamics.lidlbooking.api.mapper.StudioControllerMapper;
import com.griddynamics.lidlbooking.domain.model.Booking;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.service.BookingService;
import com.griddynamics.lidlbooking.domain.service.StudioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/booking")
@Tag(name = "Booking API")
public class BookingController {

    private final StudioService studioService;
    private final StudioControllerMapper studioMapper;
    private final BookingControllerMapper mapper;
    private final BookingService bookingService;


    @Autowired
    public BookingController(final BookingService bookingService, final BookingControllerMapper mapper,
                              final StudioService studioService, final StudioControllerMapper studioMapper) {
        this.studioService = studioService;
        this.mapper = mapper;
        this.studioMapper = studioMapper;
        this.bookingService = bookingService;
    }


    @GetMapping()
    @Operation(summary = "Find all studios for booking", description = "Find all studios that can be booked")
    public List<StudioDTO> getStudiosForBooking() {
        List<Studio> studios = studioService.findAllForBooking();
        return studioMapper.studioListToStudioDTOList(studios);
    }


    @PostMapping(path = "/studio/{id}")
    @Operation(summary = "Book a studio", description = "Book a studio in a specific date range")
    public BookingDto bookStudio(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id,
                                 @Valid @RequestBody final BookingDto bookingDto) {
        Booking booking = mapper.bookingDtoToBooking(bookingDto);
        return mapper.bookingToBookingDto(bookingService.bookStudio(id, booking));
    }


    @GetMapping(path = "/search/date-range/studio/{id}")
    @Operation(summary = "Get all bookings for a provided studio", description = "Get all bookings for a provided studio")
    public List<BookingDto> searchBookingsForStudioByDate(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id,
                                                    @Valid @RequestBody final SearchBookingDto searchBookingDto) {
        List<Booking> bookings = bookingService.searchByStudio(id, searchBookingDto.getSeasonIdList(),
                searchBookingDto.getStartDate(), searchBookingDto.getEndDate());
        return mapper.bookingListToBookingDtoList(bookings);
    }


    @GetMapping(path = "/search/length/studio/{id}")
    @Operation(summary = "Get all bookings for a provided studio", description = "Get all bookings for a provided studio")
    public List<BookingDto> searchBookingsForStudioByLength(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id,
                                                    @Valid @RequestBody final SearchBookingDto searchBookingDto) {
        List<Booking> bookings = bookingService.searchByStudio(id, searchBookingDto.getSeasonIdList(),
                searchBookingDto.getLengthOfStay());
        return mapper.bookingListToBookingDtoList(bookings);
    }


    @GetMapping(path = "/search/start-date/studio/{id}")
    @Operation(summary = "Get all bookings for a provided studio", description = "Get all bookings for a provided studio")
    public List<BookingDto> searchBookingsForStudioByStartDate(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id,
                                                    @Valid @RequestBody final SearchBookingDto searchBookingDto) {
        List<Booking> bookings = bookingService.searchByStudioAfter(id, searchBookingDto.getSeasonIdList(),
                searchBookingDto.getStartDate());
        return mapper.bookingListToBookingDtoList(bookings);
    }


    @GetMapping(path = "/search/end-date/studio/{id}")
    @Operation(summary = "Get all bookings for a provided studio", description = "Get all bookings for a provided studio")
    public List<BookingDto> searchBookingsForStudioByEndDate(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id,
                                                    @Valid @RequestBody final SearchBookingDto searchBookingDto) {
        List<Booking> bookings = bookingService.searchByStudio(id, searchBookingDto.getSeasonIdList(),
                searchBookingDto.getEndDate());
        return mapper.bookingListToBookingDtoList(bookings);
    }


    @PostMapping(path = "/disable/studio/{id}")
    @Operation(summary = "Disable a studio for booking", description = "Disable a studio for booking in a "
            + "provided date range")
    public BookingDto disableStudioForBooking(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id,
                                 @Valid @RequestBody final BookingDto bookingDto) {
        Booking booking = mapper.bookingDtoToBooking(bookingDto);
        return mapper.bookingToBookingDto(bookingService.disableStudioForBooking(id, booking));
    }
}
