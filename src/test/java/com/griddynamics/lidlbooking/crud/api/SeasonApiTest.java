package com.griddynamics.lidlbooking.crud.api;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.model.Season;
import com.griddynamics.lidlbooking.domain.service.SeasonService;
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
import java.util.List;
import java.util.stream.Stream;

import static com.griddynamics.lidlbooking.crud.api.Commons.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ExtendWith(MockitoExtension.class)
public class SeasonApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeasonService service;


    private Stream<Arguments> getSource() {
        return Stream.of(
                Arguments.of(getFindUrl(), status().isOk())
        );
    }

    @ParameterizedTest
    @MethodSource("getSource")
    void getTest(String url, ResultMatcher status) throws Exception {
        when(service.findAll()).thenReturn(List.of(getSeason()));

        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> postSource() {
        return Stream.of(
                Arguments.of(getCreateUrl(), getSeason(), status().isOk()),
                Arguments.of(getCreateUrl(), null, status().isBadRequest()),
                Arguments.of(getCreateUrl(), getSeasonInvalidName(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getSeasonInvalidFromDate(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getSeasonInvalidToDate(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getSeasonInvalidPricePerNight(), status().isBadRequest())
        );
    }

    @ParameterizedTest
    @MethodSource("postSource")
    void postTest(String url, Season season, ResultMatcher status) throws Exception {
        when(service.create(getSeason())).thenReturn(getSeason());

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(season))).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> postNoBodySource() {
        return Stream.of(
                Arguments.of(getUpdateStudioSeasonUrl() + 1L + "/studio/" + 1L, status().isOk()),
                Arguments.of(getUpdateStudioSeasonUrl() + null + "/studio/" + 1L, status().isBadRequest()),
                Arguments.of(getUpdateStudioSeasonUrl() + 1L + "/studio/" + null, status().isBadRequest()),
                Arguments.of(getUpdateStudioSeasonUrl() + null + "/studio/" + null, status().isBadRequest()),
                Arguments.of(getUpdateStudioSeasonUrl() + 1L + "/studio/" + 0L, status().isNotFound()),
                Arguments.of(getUpdateStudioSeasonUrl() + 0L + "/studio/" + 1L, status().isNotFound())
        );
    }

    @ParameterizedTest
    @MethodSource("postNoBodySource")
    void postNoBodyTest(String url, ResultMatcher status) throws Exception {
        when(service.matchSeasonStudio(1L, 1L)).thenReturn(getSeason());

        when(service.matchSeasonStudio(0L, 1L)).thenThrow(ElementNotFoundException.class);
        when(service.matchSeasonStudio(1L, 0L)).thenThrow(ElementNotFoundException.class);

        when(service.matchSeasonStudio(null, 1L)).thenThrow(BadRequestException.class);
        when(service.matchSeasonStudio(1L, null)).thenThrow(BadRequestException.class);
        when(service.matchSeasonStudio(null, null)).thenThrow(BadRequestException.class);

        this.mockMvc.perform(post(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> putSource() {
        return Stream.of(
                Arguments.of(getUpdateUrl() + 1L, getSeason(), status().isOk()),
                Arguments.of(getUpdateUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getSeasonInvalidName(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getSeasonInvalidFromDate(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getSeasonInvalidToDate(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getSeasonInvalidPricePerNight(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 0L, getSeason(), status().isBadRequest())
                );
    }

    @ParameterizedTest
    @MethodSource("putSource")
    void putTest(String url, Season season, ResultMatcher status) throws Exception {
        when(service.update(1L, getSeason())).thenReturn(getSeason());

        when(service.update(0L, getSeason())).thenThrow(BadRequestException.class);

        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(season))).andDo(print()).andExpect(status);
    }

    private static Season getSeason() {
        return new Season(1L, "Alps Ski Season 22/23",
                LocalDate.of(2022, 11, 15), LocalDate.of(2023, 05, 22),
                150, new BookingProvider());
    }

    private static Season getSeasonInvalidName() {
        return new Season(1L, "",
                LocalDate.of(2022, 11, 15), LocalDate.of(2023, 05, 22),
                150, new BookingProvider());
    }

    private static Season getSeasonInvalidFromDate() {
        return new Season(1L, "", null, LocalDate.of(2023, 05, 22),
                150, new BookingProvider());
    }

    private static Season getSeasonInvalidToDate() {
        return new Season(1L, "",
                LocalDate.of(2022, 11, 15), null,
                150, new BookingProvider());
    }

    private static Season getSeasonInvalidPricePerNight() {
        return new Season(1L, "",
                LocalDate.of(2022, 11, 15), LocalDate.of(2023, 05, 22),
                -100, new BookingProvider());
    }

    private static String getFindUrl() {
        return "/api/seasons";
    }

    private static String getUpdateStudioSeasonUrl() {
        return "/api/seasons/";
    }

    private static String getCreateUrl() {
        return "/api/seasons";
    }

    private static String getUpdateUrl() {
        return "/api/seasons/";
    }

}
