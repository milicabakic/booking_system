package com.griddynamics.lidlbooking.crud.api;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import com.griddynamics.lidlbooking.domain.model.structure.BookingProviderStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CityStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CountryStructure;
import com.griddynamics.lidlbooking.domain.service.ServiceFacilityService;
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

import java.time.OffsetTime;
import java.util.List;
import java.util.stream.Stream;

import static com.griddynamics.lidlbooking.crud.api.Commons.asJsonString;
import static org.mockito.Mockito.doThrow;
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
public class ServiceFacilityApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceFacilityService serviceFacilityService;

    private static ServiceFacility getServiceFacility() {

        return new ServiceFacility(1L, "address", new CityStructure(), new CountryStructure(),
                "11000", 4, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityCityNotInCountry() {

        return new ServiceFacility(2L, "address", new CityStructure(), new CountryStructure(),
                "11000", 4, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityNullCity() {

        return new ServiceFacility(3L, "address", null, new CountryStructure(),
                "11000", 4, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityNullCountry() {

        return new ServiceFacility(4L, "address", new CityStructure(), null,
                "11000", 4, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityNullBookingProvider() {

        return new ServiceFacility(5L, "address", new CityStructure(), new CountryStructure(),
                "11000", 4, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                null);
    }

    private static ServiceFacility getServiceFacilityInvalidAddress() {

        return new ServiceFacility(6L, "", new CityStructure(), new CountryStructure(),
                "11000", 4, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityInvalidPostalCode() {

        return new ServiceFacility(7L, "address", new CityStructure(), new CountryStructure(),
                "0", 4, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityInvalidNumberOfFloors() {

        return new ServiceFacility(8L, "address", new CityStructure(), new CountryStructure(),
                "11000", -1, "name", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityInvalidName() {

        return new ServiceFacility(9L, "address", new CityStructure(), new CountryStructure(),
                "11000", 4, "", "+381 00 000 0000",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityInvalidPhone() {

        return new ServiceFacility(10L, "address", new CityStructure(), new CountryStructure(),
                "11000", 4, "name", "+331",
                "description", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static ServiceFacility getServiceFacilityInvalidDescription() {

        return new ServiceFacility(11L, "address", new CityStructure(), new CountryStructure(),
                "11000", 4, "name", "+381 00 000 0000",
                "", OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"),
                new BookingProviderStructure());
    }

    private static String getFindUrl() {
        return "/api/facility/";
    }

    private static String getDeleteUrl() {
        return "/api/facility/";
    }

    private static String getCreateUrl() {
        return "/api/facility/";
    }

    private static String getUpdateUrl() {
        return "/api/facility/";
    }

    private Stream<Arguments> getSource() {
        return Stream.of(
                Arguments.of(getFindUrl() + 1L, status().isOk()),
                Arguments.of(getFindUrl() + null, status().isBadRequest()),
                Arguments.of(getFindUrl() + 0L, status().isNotFound()),
                Arguments.of(getFindUrl() + "all", status().isOk())
        );
    }

    @ParameterizedTest
    @MethodSource("getSource")
    void getTest(String url, ResultMatcher status) throws Exception {
        when(serviceFacilityService.getFacilityById(1L)).thenReturn(getServiceFacility());
        when(serviceFacilityService.getFacilityById(0L)).thenThrow(ElementNotFoundException.class);
        when(serviceFacilityService.getFacilityById(null)).thenThrow(BadRequestException.class);
        when(serviceFacilityService.getFacilities()).thenReturn(List.of(getServiceFacility()));

        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> deleteSource() {
        return Stream.of(
                Arguments.of(getDeleteUrl() + 1L, status().isOk()),
                Arguments.of(getDeleteUrl() + null, status().isBadRequest()),
                Arguments.of(getDeleteUrl() + 0L, status().isNotFound()),
                Arguments.of(getDeleteUrl() + "all", status().isOk())
        );
    }

    @ParameterizedTest
    @MethodSource("deleteSource")
    void deleteTest(String url, ResultMatcher status) throws Exception {
        doThrow(ElementNotFoundException.class).when(serviceFacilityService).deleteFacility(0L);
        doThrow(BadRequestException.class).when(serviceFacilityService).deleteFacility(null);

        this.mockMvc.perform(delete(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> postSource() {
        return Stream.of(
                Arguments.of(getCreateUrl(), getServiceFacility(), status().isOk()),
                Arguments.of(getCreateUrl(), null, status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityNullCity(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityNullCountry(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityNullBookingProvider(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityInvalidAddress(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityInvalidName(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityInvalidDescription(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityInvalidPhone(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityInvalidPostalCode(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityInvalidNumberOfFloors(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getServiceFacilityCityNotInCountry(), status().isBadRequest())
        );
    }

    @ParameterizedTest
    @MethodSource("postSource")
    void postTest(String url, ServiceFacility facility, ResultMatcher status) throws Exception {
        when(serviceFacilityService.createFacility(getServiceFacility())).thenReturn(getServiceFacility());
        when(serviceFacilityService.createFacility(getServiceFacilityCityNotInCountry()))
                .thenThrow(BadRequestException.class);

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(facility))).andDo(print()).andExpect(status);

    }


    private Stream<Arguments> putSource() {
        return Stream.of(
                Arguments.of(getUpdateUrl() + 1L, getServiceFacility(), status().isOk()),
                Arguments.of(getUpdateUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityNullCity(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityNullCountry(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityNullBookingProvider(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityInvalidAddress(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityInvalidName(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityInvalidDescription(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityInvalidPhone(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityInvalidPostalCode(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityInvalidNumberOfFloors(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getServiceFacilityCityNotInCountry(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 0L, getServiceFacility(), status().isNotFound())
        );
    }

    @ParameterizedTest
    @MethodSource("putSource")
    void putTest(String url, ServiceFacility facility, ResultMatcher status) throws Exception {

        when(serviceFacilityService.updateFacility(getServiceFacility(), 1L)).thenReturn(getServiceFacility());
        when(serviceFacilityService.updateFacility(getServiceFacility(), 0L))
                .thenThrow(ElementNotFoundException.class);
        when(serviceFacilityService.updateFacility(getServiceFacilityCityNotInCountry(), 1L))
                .thenThrow(BadRequestException.class);


        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(facility))).andDo(print()).andExpect(status);

    }
}
