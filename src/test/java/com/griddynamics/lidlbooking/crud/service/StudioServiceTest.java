package com.griddynamics.lidlbooking.crud.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.griddynamics.lidlbooking.business_logic.service.JWTService;
import com.griddynamics.lidlbooking.commons.BedType;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.model.structure.ServiceFacilityStructure;
import com.griddynamics.lidlbooking.domain.service.StudioService;
import com.griddynamics.lidlbooking.persistance.entity.StudioEntity;
import com.griddynamics.lidlbooking.persistance.repository.ServiceFacilityRepository;
import com.griddynamics.lidlbooking.persistance.repository.StudioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.Random.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StudioServiceTest {

    @Autowired
    @InjectMocks
    private StudioService service;

    @Autowired
    private StudioRepository repository;

    @Autowired
    private ServiceFacilityRepository facilityRepository;

    @Mock
    private JWTService jwtService;


    @BeforeEach
    void setUp() {
        when(jwtService.whoAmI()).thenReturn("nikola");
    }

    //Colum beds is set to null, it isn't tested
    @Nested
    class Create {
        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioCreate.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createStudioTest() {
            // Given: studio
            // When: createStudio
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(1L);
            Studio studio = new Studio(0L, "name", 3, 6, null, facility);

            service.createStudio(studio);
            // Then: studio should be created
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createStudioInvalidFacilityTest() {

            // Given: studio, facility doesn't exist
            // When: createStudio
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(0L);
            Studio studio = new Studio(0L, "name", 3, 6, null, facility);

            assertThrows(ElementNotFoundException.class, () -> service.createStudio(studio));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createStudioNullTest() {

            // Given: null
            // When: createStudio
            assertThrows(BadRequestException.class, () -> service.createStudio(null));
            // Then: throw error
        }
    }

    @Nested
    class Read {
        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void findByIdTest() {
            // Given: studio exist
            // When: getStudioById
            Studio studio = service.findById(1L);
            // Then: return studio
            assertAll(() -> assertEquals(1, studio.getId()),
                    () -> assertEquals("Studio1", studio.getName()),
                    () -> assertEquals(4, studio.getNumberOfRooms()),
                    () -> assertEquals(8, studio.getNumberOfPeople()),
                    () -> assertEquals(1, studio.getServiceFacility().getId()));
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void findByIdInvalidIdTest() {
            // Given: studio doesn't exist
            // When: getStudioById
            // Then: throw error
            assertThrows(ElementNotFoundException.class, () -> service.findById(-1L));
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void findByIdNullTest() {
            // Given: null
            // When: getStudioById
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.findById(null));

        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void findAllTest() {
            // Given: studios exist
            // When: getStudios
            List<Studio> list = service.findAll();
            // Then: return studios
            assertEquals(3, list.size());


        }

        @Test
        @DatabaseSetup("classpath:studio/studioEmpty.xml")
        void findAllEmptyTest() {
            // Given: studios don't exist
            // When: getStudios
            List<Studio> list = service.findAll();
            // Then: return empty
            assertEquals(0, list.size());

        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void findAllForBookingTest() {
            // When: findAllForBooking
            List<Studio> list = service.findAllForBooking();
            // Then: return studios
            assertEquals(1, list.size());


        }

        @Test
        @DatabaseSetup("classpath:studio/studioEmpty.xml")
        void findAllForBookingEmptyTest() {
            // When: findAllForBooking
            List<Studio> list = service.findAllForBooking();
            // Then: return empty
            assertEquals(0, list.size());

        }
    }

    @Nested
    class Update {
        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioUpdate.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateStudioTest() {
            // Given: studio
            // When: updateStudio
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(1L);
            Studio studio = new Studio(0L, "name", 5, 9, null, facility);

            service.updateStudio(studio, 3L);
            // Then: studio should be updated
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateStudioInvalidFacilityTest() {

            // Given: studio, facility doesn't exist
            // When: updateStudio
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(0L);
            Studio studio = new Studio(0L, "name", 3, 6, null, facility);

            assertThrows(ElementNotFoundException.class, () -> service.updateStudio(studio, 3L));
            // Then: throw error
        }


        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateStudioInvalidIdTest() {

            // Given: studio, invalid id
            // When: updateStudio
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(1L);
            Studio studio = new Studio(0L, "name", 5, 9, null, facility);

            assertThrows(ElementNotFoundException.class, () -> service.updateStudio(studio, -1L));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateStudioNullTest() {

            // Given: null
            // When: updateStudio
            assertThrows(BadRequestException.class, () -> service.updateStudio(null, 3L));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
                value = "classpath:studio/studioUpdateAmenities.xml")
        void updateStudioAmenitiesTest() {

            //Given: studio id, amenities name list
            //When: updateStudioAmenities
            Long studioId1 = 1L;
            List<String> amenitiesNameStudio1 = Arrays.asList("Breakfast", "Bathroom");
            Studio studioClassBB = service.updateStudioAmenities(studioId1, amenitiesNameStudio1);

            Long studioId2 = 2L;
            List<String> amenitiesNameStudio2 = Arrays.asList("Breakfast", "Dinner", "Bathroom");
            Studio studioClassHB = service.updateStudioAmenities(studioId2, amenitiesNameStudio2);

            Long studioId3 = 3L;
            List<String> amenitiesNameStudio3 = Arrays.asList("Breakfast", "Lunch", "Dinner", "Bathroom");
            Studio studioClassFB = service.updateStudioAmenities(studioId3, amenitiesNameStudio3);


            assertAll(() -> assertEquals("BB", studioClassBB.getClassificationType()),
                    () -> assertEquals("HB", studioClassHB.getClassificationType()),
                    () -> assertEquals("FB", studioClassFB.getClassificationType()));
            //Then: studio should be classified
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
                value = "classpath:studio/studioSampleData.xml")
        void updateStudioAmenitiesEmptyTest() {

            //Given: studio id, amenities name list
            //When: updateStudioAmenities
            Long studioId1 = 1L;
            List<String> amenitiesNameStudio1 = new ArrayList<>();
            Studio studioClassBB = service.updateStudioAmenities(studioId1, amenitiesNameStudio1);

            assertAll(() -> assertEquals("RO", studioClassBB.getClassificationType()));
            //Then: studio should be classified as None
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
                value = "classpath:studio/studioSampleData.xml")
        void updateStudioAmenitiesNullTest() {

            //Given: studio id, amenities name list
            //When: updateStudioAmenities
            assertThrows(BadRequestException.class, () -> service.updateStudioAmenities(1L, null));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
                value = "classpath:studio/studioSampleData.xml")
        void updateStudioAmenitiesInvalidTest() {

            //Given: studio id, amenities name list
            //When: updateStudioAmenities

            assertThrows(ElementNotFoundException.class, () -> service.updateStudioAmenities(-1L, new ArrayList<>()));
            // Then: throw error
        }

    }

    @Nested
    class Delete {
        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioDelete.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteByIdTest() {
            // Given: studio exist
            // When: deleteStudio
            service.deleteStudio(3L);
            // Then: studio should not be present
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteByIdInvalidIdTest() {
            // Given: studio doesn't exist
            // When: deleteStudio
            // Then: throw error
            assertThrows(ElementNotFoundException.class, () -> service.deleteStudio(-1L));
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteByIdNullTest() {
            // Given: null
            // When: deleteStudio
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.deleteStudio(null));
        }


        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        @ExpectedDatabase(value = "classpath:studio/studioEmpty.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void deleteAllTest() {
            // Given: studios exists
            // When: deleteAll studios
            service.deleteAllStudios();
            // Then: studios should not be present

        }
    }

    //Testing colum beds
    @Nested
    class TestingBedsType {

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void createStudioBedsTest() {
            // Given: studio
            // When: createStudio
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(1L);
            Studio studio = new Studio(0L, "name", 3, 6,
                    new BedType[]{BedType.full_xl, BedType.single, BedType.single}, facility);

            studio = service.createStudio(studio);

            Studio studioToTest = service.findById(studio.getId());

            List<BedType> bedTypeList = Arrays.stream(studioToTest.getBeds()).toList();

            assertEquals(3, bedTypeList.size());
            assertTrue(bedTypeList.contains(BedType.full_xl));
            assertTrue(bedTypeList.contains(BedType.single));
            assertEquals(2, bedTypeList.stream().filter(bedType -> bedType.equals(BedType.single)).count());

            // Then: studio should be created
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void findByIdBedsTest() {
            //Adding a studio to find a test
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(1L);
            StudioEntity entity = new StudioEntity();
            entity.setName("name");
            entity.setNumberOfRooms(3);
            entity.setNumberOfPeople(6);
            entity.setServiceFacility(facilityRepository.findById(1L).orElseThrow());
            entity.setBeds(new BedType[]{BedType.full_xl, BedType.single, BedType.single});
            entity = repository.save(entity);
            // Given: id
            // When: findById
            Studio studioToTest = service.findById(entity.getId());

            List<BedType> bedTypeList = Arrays.stream(studioToTest.getBeds()).toList();
            assertEquals(3, bedTypeList.size());
            assertTrue(bedTypeList.contains(BedType.full_xl));
            assertTrue(bedTypeList.contains(BedType.single));
            assertEquals(2, bedTypeList.stream().filter(bedType -> bedType.equals(BedType.single)).count());
            // Then: studio should be present
        }

        @Test
        @DatabaseSetup("classpath:studio/studioSampleData.xml")
        void updateStudioBedsTest() {
            // Given: studio
            // When: updateStudio
            ServiceFacilityStructure facility = new ServiceFacilityStructure();
            facility.setId(1L);
            Studio studio = new Studio(0L, "name", 3, 6,
                    new BedType[]{BedType.full_xl, BedType.single, BedType.single}, facility);

            studio = service.updateStudio(studio, 3L);

            Studio studioToTest = service.findById(studio.getId());

            List<BedType> bedTypeList = Arrays.stream(studioToTest.getBeds()).toList();

            assertEquals(3, bedTypeList.size());
            assertTrue(bedTypeList.contains(BedType.full_xl));
            assertTrue(bedTypeList.contains(BedType.single));
            assertEquals(2, bedTypeList.stream().filter(bedType -> bedType.equals(BedType.single)).count());

            // Then: studio should be updated
        }
    }

}
