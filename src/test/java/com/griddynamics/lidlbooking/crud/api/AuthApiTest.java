package com.griddynamics.lidlbooking.crud.api;

import com.griddynamics.lidlbooking.api.dto.users.ProviderRegistrationFormDTO;
import com.griddynamics.lidlbooking.api.dto.users.UserRegistrationFormDTO;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
import com.griddynamics.lidlbooking.domain.service.BookingUserService;
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
import static org.mockito.Mockito.when;
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
public class AuthApiTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingUserService userService;
    @MockBean
    private BookingProviderService providerService;

    private static UserRegistrationFormDTO getUserRegistrationForm() {
        return new UserRegistrationFormDTO("username", "password", "first",
                "last", "phone", "email");
    }

    private static BookingUser getBookingUser() {
        return new BookingUser(1L, "first", "last", "phone",
                "email", "username", "password");
    }

    private static ProviderRegistrationFormDTO getProviderRegistrationForm() {
        return new ProviderRegistrationFormDTO("provider", "password", "first",
                "last", "jmbg", "phone", "email");
    }

    private static BookingProvider getBookingProvider() {
        return new BookingProvider(1L, "first", "last", "jmbg", "phone",
                "email", "provider", "password");
    }

    private static String getUserPostUrl() {
        return "/auth/user/register";
    }

    private static String getProviderPostUrl() {
        return "/auth/provider/register";
    }

    private Stream<Arguments> userPostSource() {
        return Stream.of(
                Arguments.of(getUserPostUrl(), getUserRegistrationForm(), status().isOk()),
                Arguments.of(getUserPostUrl(), null, status().isBadRequest())
        );

    }

    @ParameterizedTest
    @MethodSource("userPostSource")
    void userPostTest(String url, UserRegistrationFormDTO form, ResultMatcher status) throws Exception {
        when(userService.create(getBookingUser())).thenReturn(getBookingUser());

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(form))).andDo(print()).andExpect(status);
    }

    private Stream<Arguments> providerPostSource() {
        return Stream.of(
                Arguments.of(getProviderPostUrl(), getProviderRegistrationForm(), status().isOk()),
                Arguments.of(getProviderPostUrl(), null, status().isBadRequest())
        );

    }

    @ParameterizedTest
    @MethodSource("providerPostSource")
    void providerPostTest(String url, ProviderRegistrationFormDTO form, ResultMatcher status) throws Exception {
        when(providerService.create(getBookingProvider())).thenReturn(getBookingProvider());

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(form))).andDo(print()).andExpect(status);
    }
}
