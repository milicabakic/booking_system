package com.griddynamics.lidlbooking.crud.api;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.domain.model.structure.AmenityStructure;
import com.griddynamics.lidlbooking.domain.service.AmenityService;
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
public class AmenityApiTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AmenityService amenityService;

    private static Amenity getAmenity() {
        return new Amenity(1L, "Kuhinja", null);
    }

    private static Amenity getAmenityBadParent() {
        return new Amenity(3L, "Frizider", new AmenityStructure(null, "Kuhinja"));
    }

    private static Amenity getAmenityNoName() {
        return new Amenity(1L, null, null);
    }

    private static String getFindUrl() {
        return "/api/amenity/";
    }

    private static String getDeleteUrl() {
        return "/api/amenity/";
    }

    private static String getCreateUrl() {
        return "/api/amenity/";
    }

    private static String getUpdateUrl() {
        return "/api/amenity/";
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
        when(amenityService.getAmenityById(1L)).thenReturn(getAmenity());
        when(amenityService.getAmenityById(0L)).thenThrow(ElementNotFoundException.class);
        when(amenityService.getAmenityById(null)).thenThrow(BadRequestException.class);
        when(amenityService.getAmenities()).thenReturn(List.of(getAmenity()));

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
        doThrow(ElementNotFoundException.class).when(amenityService).deleteAmenity(0L);
        doThrow(BadRequestException.class).when(amenityService).deleteAmenity(null);

        this.mockMvc.perform(delete(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> postSource() {
        return Stream.of(
                Arguments.of(getCreateUrl(), getAmenity(), status().isOk()),
                Arguments.of(getCreateUrl(), null, status().isBadRequest()),
                Arguments.of(getCreateUrl(), getAmenityNoName(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getAmenityBadParent(), status().isNotFound()));
    }

    @ParameterizedTest
    @MethodSource("postSource")
    void postTest(String url, Amenity amenity, ResultMatcher status) throws Exception {
        when(amenityService.createAmenity(getAmenity())).thenReturn(getAmenity());
        when(amenityService.createAmenity(getAmenityBadParent())).thenThrow(ElementNotFoundException.class);

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(amenity))).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> putSource() {
        return Stream.of(
                Arguments.of(getUpdateUrl() + 1L, getAmenity(), status().isOk()),
                Arguments.of(getUpdateUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getAmenityNoName(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 0L, getAmenity(), status().isNotFound()),
                Arguments.of(getUpdateUrl() + 1L, getAmenityBadParent(), status().isNotFound()));
    }

    @ParameterizedTest
    @MethodSource("putSource")
    void putTest(String url, Amenity amenity, ResultMatcher status) throws Exception {

        when(amenityService.updateAmenity(getAmenity(), 1L)).thenReturn(getAmenity());
        when(amenityService.updateAmenity(getAmenity(), 0L)).thenThrow(ElementNotFoundException.class);
        when(amenityService.updateAmenity(null, 1L)).thenThrow(BadRequestException.class);
        when(amenityService.updateAmenity(getAmenityBadParent(), 1L)).thenThrow(ElementNotFoundException.class);

        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(amenity))).andDo(print()).andExpect(status);
    }
}
