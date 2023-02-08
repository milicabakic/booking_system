package com.griddynamics.lidlbooking.crud.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.griddynamics.lidlbooking.business_logic.service.ServiceFacilityServiceImpl;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import com.griddynamics.lidlbooking.domain.model.structure.BookingProviderStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CityStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CountryStructure;
import com.griddynamics.lidlbooking.domain.service.ServiceFacilityService;
import com.griddynamics.lidlbooking.persistance.manager.ServiceFacilityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.OffsetTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.Random.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceFacilityServiceTest {

    @Autowired
    private ServiceFacilityService service;


    @Nested
    class Create {

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilityCreate.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createFacilityTest() {

            // Given: facility
            // When: createFacility
            CountryStructure countryStructure = new CountryStructure("SRB", "Serbia");
            CityStructure cityStructure = new CityStructure(1L, "Belgrade", "Belgrade",
                    countryStructure);
            BookingProviderStructure bookingProvider = new BookingProviderStructure();
            bookingProvider.setId(1L);
            ServiceFacility serviceFacility = new ServiceFacility(0L, "address4", cityStructure, countryStructure,
                    "110004", 4, "Hotel4", "+381 64 444 4444", "4",
                    OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"), bookingProvider);

            service.createFacility(serviceFacility);
            // Then: facility should be created
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilitySampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createFacilityInvalidCityTest() {

            // Given: facility, city doesn't belong to country
            // When: createFacility
            CountryStructure countryStructure = new CountryStructure("SRB", "Serbia");
            CityStructure cityStructure = new CityStructure(2L, "Tokyo", "Tokyo",
                    countryStructure);
            BookingProviderStructure bookingProvider = new BookingProviderStructure();
            bookingProvider.setId(1L);
            ServiceFacility serviceFacility = new ServiceFacility(0L, "address4", cityStructure, countryStructure,
                    "110004", 4, "Hotel4", "+381 64 444 4444", "4",
                    OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"), bookingProvider);

            assertThrows(BadRequestException.class, () -> service.createFacility(serviceFacility));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilitySampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createFacilityNullTest() {

            // Given: null
            // When: createFacility
            assertThrows(BadRequestException.class, () -> service.createFacility(null));
            // Then: throw error
        }

    }

    @Nested
    class Read {
        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        void findByIdTest() {
            // Given: facility exist
            // When: getFacilityById
            ServiceFacility facility = service.getFacilityById(1L);
            // Then: return facility
            assertAll(() -> assertEquals(1, facility.getId()),
                    () -> assertEquals("address", facility.getAddress()),
                    () -> assertEquals("11000", facility.getPostalCode()),
                    () -> assertEquals(5, facility.getNumberOfFloors()),
                    () -> assertEquals("Hotel1", facility.getName()),
                    () -> assertEquals("+381 64 999 9999", facility.getContactPhone()),
                    () -> assertEquals("description", facility.getDescription()),
                    () -> assertEquals(OffsetTime.parse("12:00:00+01:00"), facility.getCheckinTime()),
                    () -> assertEquals(OffsetTime.parse("10:00:00+01:00"), facility.getCheckoutTime()),
                    () -> assertEquals(1, facility.getCity().getId()),
                    () -> assertEquals("SRB", facility.getCountry().getIso3()),
                    () -> assertEquals(1, facility.getBookingProvider().getId()));
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        void findByIdInvalidIdTest() {
            // Given: facility doesn't exist
            // When: getFacilityById
            // Then: throw error
            assertThrows(ElementNotFoundException.class, () -> service.getFacilityById(-1L));
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        void findByIdNullTest() {
            // Given: null
            // When: getFacilityById
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.getFacilityById(null));

        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        void findAllTest() {
            // Given: facilities exist
            // When: findAll
            List<ServiceFacility> list = service.getFacilities();
            // Then: return facilities
            assertEquals(3, list.size());


        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilityEmpty.xml")
        void findAllEmptyTest() {
            // Given: facilities don't exist
            // When: findAll
            List<ServiceFacility> list = service.getFacilities();
            // Then: return empty
            assertEquals(0, list.size());

        }
    }

    @Nested
    class Update {
        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilityUpdate.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateFacilityTest() {

            // Given: facility
            // When: updateFacility
            CountryStructure countryStructure = new CountryStructure("SRB", "Serbia");
            CityStructure cityStructure = new CityStructure(1L, "Belgrade", "Belgrade",
                    countryStructure);
            BookingProviderStructure bookingProvider = new BookingProviderStructure();
            bookingProvider.setId(1L);
            ServiceFacility serviceFacility = new ServiceFacility(3L, "address4", cityStructure, countryStructure,
                    "110004", 4, "Hotel4", "+381 64 444 4444", "4",
                    OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"), bookingProvider);

            service.updateFacility(serviceFacility, 3L);
            // Then: facility should be updated
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilitySampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateFacilityInvalidIdTest() {

            // Given: facility and invalid id
            // When: updateFacility
            CountryStructure countryStructure = new CountryStructure("SRB", "Serbia");
            CityStructure cityStructure = new CityStructure(1L, "Belgrade", "Belgrade",
                    countryStructure);
            BookingProviderStructure bookingProvider = new BookingProviderStructure();
            bookingProvider.setId(1L);
            ServiceFacility serviceFacility = new ServiceFacility(0L, "address4", cityStructure, countryStructure,
                    "110004", 4, "Hotel4", "+381 64 444 4444", "4",
                    OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"), bookingProvider);

            assertThrows(ElementNotFoundException.class, () -> service.updateFacility(serviceFacility, -1L));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilitySampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateFacilityInvalidCityTest() {

            // Given: facility, city doesn't belong to country
            // When: updateFacility
            CountryStructure countryStructure = new CountryStructure("SRB", "Serbia");
            CityStructure cityStructure = new CityStructure(2L, "Tokyo", "Tokyo",
                    countryStructure);
            BookingProviderStructure bookingProvider = new BookingProviderStructure();
            bookingProvider.setId(1L);
            ServiceFacility serviceFacility = new ServiceFacility(0L, "address4", cityStructure, countryStructure,
                    "110004", 4, "Hotel4", "+381 64 444 4444", "4",
                    OffsetTime.parse("12:00:00+01:00"), OffsetTime.parse("10:00:00+01:00"), bookingProvider);

            assertThrows(BadRequestException.class, () -> service.updateFacility(serviceFacility, 3L));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilitySampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateFacilityNullTest() {

            // Given: null
            // When: updateFacility
            assertThrows(BadRequestException.class, () -> service.updateFacility(null, 2L));
            // Then: throw error
        }
    }

    @Nested
    class Delete {
        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilityDelete.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteByIdTest() {
            // Given: facility exist
            // When: deleteFacility
            service.deleteFacility(3L);
            // Then: facility should not be present
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilitySampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteByIdInvalidIdTest() {
            // Given: facility doesn't exist
            // When: deleteFacility
            // Then: throw error
            assertThrows(ElementNotFoundException.class, () -> service.deleteFacility(-1L));
        }

        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilitySampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteByIdNullTest() {
            // Given: null
            // When: deleteFacility
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.deleteFacility(null));
        }


        @Test
        @DatabaseSetup("classpath:service_facility/facilitySampleData.xml")
        @ExpectedDatabase(value = "classpath:service_facility/facilityEmpty.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteAllTest() {
            // Given: facilities exists
            // When: deleteAll facilities
            service.deleteAllFacility();
            // Then: facilities should not be present

        }
    }


}
