package com.griddynamics.lidlbooking.crud.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.griddynamics.lidlbooking.business_logic.service.JWTService;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.Booking;
import com.griddynamics.lidlbooking.domain.model.BookingUser;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.service.BookingService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.Random.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookingServiceTest {

    @Autowired
    @InjectMocks
    private BookingService service;

    @Mock
    private JWTService jwtService;


    @BeforeEach
    void setUp() {
        when(jwtService.whoAmI()).thenReturn("user");
    }

    @Nested
    class Create {
        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        @ExpectedDatabase(value = "classpath:booking/bookingCreate.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createBookingTest() {
            // Given: booking
            // When: bookStudio
            Studio studio = new Studio();
            studio.setId(1L);
            BookingUser bookingUser = new BookingUser();
            bookingUser.setId(1L);
            Booking booking = new Booking(0L, LocalDate.of(2023, 02, 21),
                    LocalDate.of(2023, 02, 23), "Mocked note",
                    studio, bookingUser, 2);

            service.bookStudio(1L, booking);
            // Then: booking should be created
        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        @ExpectedDatabase(value = "classpath:booking/bookingSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createBookingInvalidTest() {

            // Given: booking, studio doesn't exist
            // When: createStudio
            Studio studio = new Studio();
            studio.setId(1L);
            BookingUser bookingUser = new BookingUser();
            bookingUser.setId(1L);
            Booking booking = new Booking(0L, LocalDate.of(2023, 02, 21),
                    LocalDate.of(2023, 02, 23), "Mocked note", studio, bookingUser, 2);

            assertThrows(ElementNotFoundException.class, () -> service.bookStudio(0L, booking));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        @ExpectedDatabase(value = "classpath:booking/bookingSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createBookingNullTest() {

            // Given: null
            // When: createStudio
            assertThrows(BadRequestException.class, () -> service.bookStudio(1L, null));
            // Then: throw error
        }
    }

    @Nested
    class Read {
        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        void findByStudioIdTest() {
            // Given: bookings exist for studio id
            // When: findByStudioId
            List<Booking> list = service.findAllByStudioId(1L);
            // Then: return bookings
            assertEquals(2, list.size());
        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        void findByStudioIdNullTest() {
            // Given: null
            // When: findByStudioId
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.findAllByStudioId(null));

        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        void findByStudioTest() {
            // Given: bookings exist for studio id
            // When: findByStudioId
            Studio studio = new Studio();
            studio.setId(1L);
            List<Booking> list = service.findAllByStudio(studio);
            // Then: return bookings
            assertEquals(2, list.size());
        }


        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        void findByStudioNullTest() {
            // Given: null
            // When: findByStudioId
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.findAllByStudio(null));

        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        void findAllTest() {
            // Given: bookings exist
            // When: findAll
            List<Booking> list = service.findAll();
            // Then: return bookings
            assertEquals(2, list.size());

        }

        @Test
        @DatabaseSetup("classpath:booking/bookingEmpty.xml")
        void findAllEmptyTest() {
            // Given: bookings don't exist
            // When: findAll
            List<Booking> list = service.findAll();
            // Then: return empty
            assertEquals(0, list.size());

        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        void searchByStudioTest() {
            // Given: bookings exist for studio id
            // When: findByStudioId
            List<Long> seasonId = new ArrayList<>();
            seasonId.add(1L);
            LocalDate fromDate = LocalDate.of(2023, 01, 02);
            LocalDate toDate = LocalDate.of(2023, 03, 31);
            LocalDate afterDate = LocalDate.of(2023, 01, 01);


            List<Booking> listLengthStay = service.searchByStudio(1L, new ArrayList<>(), 5);
            List<Booking> listLengthStayWithSeason = service.searchByStudio(1L, seasonId, 5);

            List<Booking> listDateRange = service.searchByStudio(1L, new ArrayList<>(), fromDate, toDate);
            List<Booking> listDateRangeWithSeason = service.searchByStudio(1L, seasonId, fromDate, toDate);

            List<Booking> listToDate = service.searchByStudio(1L, new ArrayList<>(), toDate);
            List<Booking> listToDateWithSeason = service.searchByStudio(1L, seasonId, toDate);

            List<Booking> listAfterDate = service.searchByStudioAfter(1L, new ArrayList<>(), afterDate);
            List<Booking> listToAfterWithSeason = service.searchByStudioAfter(1L, seasonId, afterDate);

            // Then: return bookings
            assertEquals(1, listLengthStay.size());
            assertEquals(1, listLengthStayWithSeason.size());

            assertEquals(2, listDateRange.size());
            assertEquals(2, listDateRangeWithSeason.size());

            assertEquals(2, listToDate.size());
            assertEquals(2, listToDateWithSeason.size());

            assertEquals(2, listAfterDate.size());
            assertEquals(2, listToAfterWithSeason.size());

        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        void searchByStudioNullTest() {
            // Given: null
            // When: findByStudioId
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.findAllByStudioId(null));
        }


    }

    @Nested
    class Update {
        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        @ExpectedDatabase(value = "classpath:booking/bookingDisable.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void disableBookingTest() {
            // Given: booking
            // When: disableStudioForBooking
            Studio studio = new Studio();
            studio.setId(1L);
            Booking booking = new Booking(0L, LocalDate.of(2023, 03, 15),
                    LocalDate.of(2023, 03, 25), "Studio disabled",
                    studio, null, 10);

            service.disableStudioForBooking(1L, booking);
            // Then: studio should be disabled for booking
        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        @ExpectedDatabase(value = "classpath:booking/bookingSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void disableBookingInvalidTest() {

            // Given: booking already exists
            // When: disableStudioForBooking
            Studio studio = new Studio();
            studio.setId(1L);
            Booking booking = new Booking(0L, LocalDate.of(2023, 02, 21),
                    LocalDate.of(2023, 02, 23), "Mocked note",
                    studio, null, 2);

            assertThrows(ElementNotFoundException.class, () -> service.disableStudioForBooking(0L, booking));
            // Then: throw error
        }


        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        @ExpectedDatabase(value = "classpath:booking/bookingSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void disableBookingInvalidIdTest() {

            // Given: booking, studio invalid id
            // When: disableStudioForBooking
            Studio studio = new Studio();
            studio.setId(1L);
            Booking booking = new Booking(0L, LocalDate.of(2023, 03, 15),
                    LocalDate.of(2023, 03, 25), "Studio disabled",
                    studio, null, 10);

            assertThrows(ElementNotFoundException.class, () -> service.disableStudioForBooking(-1L, booking));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:booking/bookingSampleData.xml")
        @ExpectedDatabase(value = "classpath:booking/bookingSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void disableBookingNullTest() {

            // Given: null
            // When: disableStudioForBooking
            assertThrows(BadRequestException.class, () -> service.disableStudioForBooking(1L, null));
            // Then: throw error
        }
    }

}
