package com.griddynamics.lidlbooking.crud.api;

import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
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

import java.util.stream.Stream;

import static com.griddynamics.lidlbooking.crud.api.Commons.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ExtendWith(MockitoExtension.class)
public class BookingProviderApiTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingProviderService service;

    private static BookingProvider getBookingProvider() {
        return new BookingProvider(1L, "first", "last", "jmbg", "phone",
                "email", "username", "password");
    }

    private static String getFindUrl() {
        return "/api/provider/";
    }

    private static String getUpdateUrl() {
        return "/api/provider/";
    }

    private Stream<Arguments> getSource() {
        return Stream.of(
                Arguments.of(getFindUrl() + "username", status().isOk()),
                Arguments.of(getFindUrl() + "", status().isNotFound())
        );
    }

    @ParameterizedTest
    @MethodSource("getSource")
    void getTest(String url, ResultMatcher status) throws Exception {
        when(service.findBookingProviderByUsername("username")).thenReturn(getBookingProvider());
        when(service.findBookingProviderByUsername("")).thenThrow(ElementNotFoundException.class);

        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> postSource() {
        return Stream.of(
                Arguments.of(getUpdateUrl() + 1L, getBookingProvider(), status().isOk()),
                Arguments.of(getUpdateUrl() + 1L, null, status().isBadRequest()),
                Arguments.of(getUpdateUrl() + -1L, getBookingProvider(), status().isNotFound())
        );

    }

    @ParameterizedTest
    @MethodSource("postSource")
    void postTest(String url, BookingProvider provider, ResultMatcher status) throws Exception {
        when(service.editBookingProvider(1L, getBookingProvider())).thenReturn(getBookingProvider());
        when(service.editBookingProvider(eq(-1L), any())).thenThrow(ElementNotFoundException.class);

        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(provider))).andDo(print()).andExpect(status);
    }
}
