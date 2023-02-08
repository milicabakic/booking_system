package com.griddynamics.lidlbooking.crud.api;

import com.griddynamics.lidlbooking.api.dto.search.SearchBookingDto;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.Booking;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.service.BookingService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.griddynamics.lidlbooking.crud.api.Commons.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ExtendWith(MockitoExtension.class)
public class BookingApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService service;


    private Stream<Arguments> getSource() {
        return Stream.of(
                Arguments.of(getFindUrl(), status().isOk())
        );
    }

    @ParameterizedTest
    @MethodSource("getSource")
    void getTest(String url, ResultMatcher status) throws Exception {
        when(service.findAll()).thenReturn(List.of(getBooking()));

        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> postSource() {
        return Stream.of(
                Arguments.of(getCreateUrl() + 1L, getBooking(), status().isOk()),
                Arguments.of(getCreateUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getCreateUrl() + 1L, getBookingInvalidFromDate(), status().isBadRequest()),
                Arguments.of(getCreateUrl() + 1L, getBookingInvalidToDate(), status().isBadRequest()),

                Arguments.of(getDisableUrl() + 1L, getBooking(), status().isOk()),
                Arguments.of(getDisableUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getDisableUrl() + 1L, getBookingInvalidFromDate(), status().isBadRequest()),
                Arguments.of(getDisableUrl() + 1L, getBookingInvalidToDate(), status().isBadRequest())
        );
    }

    @ParameterizedTest
    @MethodSource("postSource")
    void postTest(String url, Booking booking, ResultMatcher status) throws Exception {
        when(service.bookStudio(1L, getBooking())).thenReturn(getBooking());
        when(service.bookStudio(0L, getBooking())).thenThrow(ElementNotFoundException.class);

        when(service.disableStudioForBooking(1L, getBooking())).thenReturn(getBooking());

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(booking))).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> getSearchSource() {
        return Stream.of(
                Arguments.of(getSearchUrl() + "date-range/studio/" + 1L, getSearchBookingDto(), status().isOk()),
                Arguments.of(getSearchUrl() + "date-range/studio/" + 0L, getSearchBookingDto(), status().isNotFound()),

                Arguments.of(getSearchUrl() + "length/studio/" + 1L, getSearchBookingDto(), status().isOk()),
                Arguments.of(getSearchUrl() + "length/studio/" + 0L, getSearchBookingDto(), status().isNotFound()),

                Arguments.of(getSearchUrl() + "start-date/studio/" + 1L, getSearchBookingDto(), status().isOk()),
                Arguments.of(getSearchUrl() + "start-date/studio/" + 0L, getSearchBookingDto(), status().isNotFound()),

                Arguments.of(getSearchUrl() + "end-date/studio/" + 1L, getSearchBookingDto(), status().isOk()),
                Arguments.of(getSearchUrl() + "end-date/studio/" + 0L, getSearchBookingDto(), status().isNotFound())
        );
    }

    @ParameterizedTest
    @MethodSource("getSearchSource")
    void getSearchTest(String url, SearchBookingDto searchBooking, ResultMatcher status) throws Exception {
        when(service.searchByStudio(1L, new ArrayList<>(), getStartDate(), getEndDate())).thenReturn(List.of(getBooking()));
        when(service.searchByStudio(0L, new ArrayList<>(), getStartDate(), getEndDate())).thenThrow(ElementNotFoundException.class);

        when(service.searchByStudio(1L, new ArrayList<>(), getEndDate())).thenReturn(List.of(getBooking()));
        when(service.searchByStudio(0L, new ArrayList<>(), getEndDate())).thenThrow(ElementNotFoundException.class);

        when(service.searchByStudio(1L, new ArrayList<>(), getLengthOfStay())).thenReturn(List.of(getBooking()));
        when(service.searchByStudio(0L, new ArrayList<>(), getLengthOfStay())).thenThrow(ElementNotFoundException.class);

        when(service.searchByStudioAfter(1L, new ArrayList<>(), getStartDate())).thenReturn(List.of(getBooking()));
        when(service.searchByStudioAfter(0L, new ArrayList<>(), getStartDate())).thenThrow(ElementNotFoundException.class);


        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(searchBooking))).andDo(print()).andExpect(status);
    }


    private static Booking getBooking() {
        return new Booking(1L,
                LocalDate.of(2022, 01, 01),
                LocalDate.of(2022, 01, 10),
                "Mocked note", new Studio(1L), new BookingUser(1L), 10);
    }

    private static Booking getBookingInvalidFromDate() {
        return new Booking(1L,
                null,
                LocalDate.of(2022, 01, 10),
                "Mocked note", new Studio(1L), new BookingUser(1L), 10);
    }

    private static Booking getBookingInvalidToDate() {
        return new Booking(1L,
                LocalDate.of(2022, 01, 10),
                null,
                "Mocked note", new Studio(1L), new BookingUser(1L), 10);
    }

    private static SearchBookingDto getSearchBookingDto() {
        return new SearchBookingDto(new ArrayList<>(), LocalDate.of(2022, 12, 20),
                LocalDate.of(2022, 12, 31), 5);
    }

    private static LocalDate getStartDate() {
        return LocalDate.of(2022, 12, 20);
    }

    private static LocalDate getEndDate() {
        return LocalDate.of(2022, 12, 31);
    }

    private static int getLengthOfStay() {
        return 5;
    }

    private static String getFindUrl() {
        return "/api/booking";
    }

    private static String getDisableUrl() {
        return "/api/booking/disable/studio/";
    }

    private static String getSearchUrl() {
        return "/api/booking/search/";
    }

    private static String getCreateUrl() {
        return "/api/booking/studio/";
    }
}
