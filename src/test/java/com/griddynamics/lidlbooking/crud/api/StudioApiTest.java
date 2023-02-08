package com.griddynamics.lidlbooking.crud.api;

import com.griddynamics.lidlbooking.api.dto.StudioClassificationDTO;
import com.griddynamics.lidlbooking.commons.BedType;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.model.structure.ServiceFacilityStructure;
import com.griddynamics.lidlbooking.domain.service.StudioService;
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

import java.util.Arrays;
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
public class StudioApiTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudioService service;


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
        when(service.findById(1L)).thenReturn(getStudio());
        when(service.findById(0L)).thenThrow(ElementNotFoundException.class);
        when(service.findById(null)).thenThrow(BadRequestException.class);
        when(service.findAll()).thenReturn(List.of(getStudio()));

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
        doThrow(ElementNotFoundException.class).when(service).deleteStudio(0L);
        doThrow(BadRequestException.class).when(service).deleteStudio(null);

        this.mockMvc.perform(delete(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> postSource() {
        return Stream.of(
                Arguments.of(getCreateUrl(), getStudio(), status().isOk()),
                Arguments.of(getCreateUrl(), null, status().isBadRequest()),
                Arguments.of(getCreateUrl(), getStudioInvalidName(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getStudioInvalidBedType(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getStudioInvalidNumberOfPeople(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getStudioInvalidNumberOfRooms(), status().isBadRequest()),
                Arguments.of(getCreateUrl(), getStudioNullServiceFacility(), status().isBadRequest())
        );
    }

    @ParameterizedTest
    @MethodSource("postSource")
    void postTest(String url, Studio studio, ResultMatcher status) throws Exception {
        when(service.createStudio(getStudio())).thenReturn(getStudio());

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studio))).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> putSource() {
        return Stream.of(
                Arguments.of(getUpdateUrl() + 1L, getStudio(), status().isOk()),
                Arguments.of(getUpdateUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getStudioInvalidName(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getStudioInvalidNumberOfRooms(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getStudioInvalidBedType(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getStudioInvalidNumberOfPeople(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 1L, getStudioNullServiceFacility(), status().isBadRequest()),
                Arguments.of(getUpdateUrl() + 0L, getStudio(), status().isNotFound())
        );
    }

    @ParameterizedTest
    @MethodSource("putSource")
    void putTest(String url, Studio studio, ResultMatcher status) throws Exception {

        when(service.updateStudio(getStudio(), 1L)).thenReturn(getStudio());
        when(service.updateStudio(getStudio(), 0L)).thenThrow(ElementNotFoundException.class);

        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(studio))).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> studioClassification() {
        return Stream.of(
                Arguments.of(getUpdateWithAmenityListUrl() + 1L, getStudioClassification(), status().isOk()),
                Arguments.of(getUpdateWithAmenityListUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getUpdateWithAmenityListUrl() + 0L, getStudioClassification(), status().isNotFound())
        );
    }

    @ParameterizedTest
    @MethodSource("studioClassification")
    void studioClassificationTest(String url, StudioClassificationDTO dto, ResultMatcher status) throws Exception {
        when(service.updateStudioAmenities(0L, getAmenities())).thenThrow(ElementNotFoundException.class);

        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))).andDo(print()).andExpect(status);
    }

    private static Studio getStudio() {
        return new Studio(1L, "name", 2, 4,
                new BedType[]{BedType.single}, new ServiceFacilityStructure());
    }

    private static Studio getStudioInvalidName() {
        return new Studio(2L, "", 2, 4,
                new BedType[]{BedType.single}, new ServiceFacilityStructure());
    }

    private static Studio getStudioInvalidNumberOfRooms() {
        return new Studio(3L, "name", 0, 4,
                new BedType[]{BedType.single}, new ServiceFacilityStructure());
    }

    private static Studio getStudioInvalidNumberOfPeople() {
        return new Studio(4L, "name", 2, 0,
                new BedType[]{BedType.single}, new ServiceFacilityStructure());
    }

    private static Studio getStudioInvalidBedType() {
        return new Studio(5L, "name", 2, 4,
                new BedType[]{}, new ServiceFacilityStructure());
    }

    private static Studio getStudioNullServiceFacility() {
        return new Studio(6L, "name", 2, 4,
                new BedType[]{BedType.single}, null);
    }

    private static List<String> getAmenities() {
        return Arrays.asList("Bathroom", "Shampoo", "Breakfast", "Dinner");
    }

    private static StudioClassificationDTO getStudioClassification() {
        return new StudioClassificationDTO(getAmenities());
    }


    private static String getFindUrl() {
        return "/api/studio/";
    }

    private static String getDeleteUrl() {
        return "/api/studio/";
    }

    private static String getCreateUrl() {
        return "/api/studio";
    }

    private static String getUpdateUrl() {
        return "/api/studio/";
    }

    private static String getUpdateWithAmenityListUrl() {
        return "/api/studio/amenity/";
    }

}
