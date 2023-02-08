package com.griddynamics.lidlbooking.crud.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.griddynamics.lidlbooking.business_logic.service.AmenityServiceImpl;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.domain.model.structure.AmenityStructure;
import com.griddynamics.lidlbooking.domain.service.AmenityService;
import com.griddynamics.lidlbooking.persistance.manager.AmenityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.Random.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AmenityServiceTest {

    @Autowired
    private AmenityService amenityService;

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenityCreateChild.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void createAmenityChildTest() {
        // Given: amenity with parent
        // When: createAmenity
        Amenity amenity = new Amenity();
        amenity.setName("Tus");
        amenity.setParent(new AmenityStructure(1L, null));
        // Then: create amenity with parent
        amenityService.createAmenity(amenity);

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenityCreateParent.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void createAmenityParentTest() {
        // Given: amenity with no parent
        // When: createAmenity
        Amenity amenity = new Amenity();
        amenity.setName("Terasa");
        // Then: create amenity with no parent
        amenityService.createAmenity(amenity);

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenitySampleData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void createAmenityNoNameTest() {
        // Given: amenity with no name
        // When: createAmenity
        Amenity amenity = new Amenity();
        // Then: throw error
        assertThrows(SQLException.class, () -> amenityService.createAmenity(amenity));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenitySampleData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void createAmenityNullTest() {
        // Given: null
        // When: createAmenity
        // Then: throw error
        assertThrows(BadRequestException.class, () -> amenityService.createAmenity(null));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void findByIdTest() {
        // Given: amenity exists
        // When: findById amenity
        Amenity amenityParent = amenityService.getAmenityById(1L);

        Amenity amenityChild = amenityService.getAmenityById(3L);
        // Then: return amenity
        assertAll(() -> assertEquals("Kupatilo", amenityParent.getName()),
                () -> assertNull(amenityParent.getParent()),
                () -> assertEquals("Sampon", amenityChild.getName()),
                () -> assertEquals("Kupatilo", amenityChild.getParent().getName()));
    }


    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void findByIdInvalidIdTest() {
        // Given: amenity doesn't exist
        // When: findById amenity
        // Then: throw error
        assertThrows(ElementNotFoundException.class, () -> amenityService.getAmenityById(-1L));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void findByIdNullTest() {
        // Given: null
        // When: findById amenity
        // Then: throw error
        assertThrows(BadRequestException.class, () -> amenityService.getAmenityById(null));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void findByNameTest() {
        // Given: amenity exists
        // When: findByName amenity
        Amenity amenityParent = amenityService.getAmenityByName("Kupatilo");

        Amenity amenityChild = amenityService.getAmenityByName("Sampon");
        // Then: return amenity
        assertAll(() -> assertEquals("Kupatilo", amenityParent.getName()),
                () -> assertNull(amenityParent.getParent()),
                () -> assertEquals("Sampon", amenityChild.getName()),
                () -> assertEquals("Kupatilo", amenityChild.getParent().getName()));
    }


    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void findByNameInvalidNameTest() {
        // Given: amenity doesn't exist
        // When: findByName amenity
        // Then: throw error
        assertThrows(ElementNotFoundException.class, () -> amenityService.getAmenityByName("Ne postiji ovo ime"));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void findByNameNullTest() {
        // Given: null
        // When: findByName amenity
        // Then: throw error
        assertThrows(BadRequestException.class, () -> amenityService.getAmenityById(null));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenityUpdate.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void updateAmenityTest() {
        // Given: amenity with parent
        // When: update amenity
        Amenity amenity = new Amenity();
        amenity.setName("Tus");
        amenity.setParent(new AmenityStructure(1L, "Kupatilo"));
        amenityService.updateAmenity(amenity, 2L);
        // Then: amenity name set to name and parent to parent

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenityUpdateNoParent.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void updateAmenityNoParentTest() {
        // Given: amenity with null parent
        // When: update amenity
        Amenity amenity = new Amenity();
        amenity.setName("Tus");
        amenityService.updateAmenity(amenity, 2L);
        // Then: amenity name set to name and parent to null

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenitySampleData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void updateAmenityInvalidIdTest() {
        // Given: invalid id
        // When: update amenity
        Amenity amenity = new Amenity();
        amenity.setName("Tus");
        // Then: throw error
        assertThrows(ElementNotFoundException.class, () -> amenityService.updateAmenity(amenity, -1L));

    }


    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenitySampleData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void updateAmenityIdNullTest() {
        // Given: null id
        // When: update amenity
        Amenity amenity = new Amenity();
        amenity.setName("Tus");
        // Then: throw error
        assertThrows(BadRequestException.class, () -> amenityService.updateAmenity(amenity, null));

    }


    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenitySampleData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void updateAmenityAmenityNullTest() {
        // Given: null amenity
        // When: update amenity
        // Then: throw error
        assertThrows(BadRequestException.class, () -> amenityService.updateAmenity(null, 2L));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenityDeleteParent.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void deleteByIdParentTest() {
        // Given: amenity exists
        // When: delete amenity
        amenityService.deleteAmenity(1L);
        // Then: amenity and it's child should not be present
    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenityDeleteChild.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void deleteByIdChildTest() {
        // Given: amenity exists
        // When: delete amenity
        amenityService.deleteAmenity(3L);
        // Then: amenity should not be present
    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void deleteByIdInvalidIdTest() {
        //Given: amenity doesn't exist
        // When: delete amenity
        assertThrows(ElementNotFoundException.class, () -> amenityService.deleteAmenity((long) -1));
        // Then: throw error
    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void deleteByIdNullTest() {
        //Given: null
        // When: delete amenity
        assertThrows(BadRequestException.class, () -> amenityService.deleteAmenity(null));
        // Then: throw error
    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    void findAllTest() {
        // Given: amenities exist
        // When: findAll amenities
        List<Amenity> amenityList = amenityService.getAmenities();
        // Then: return amenities
        assertAll(() -> assertEquals(3, amenityList.size()),
                () -> assertEquals("Kupatilo", amenityList.get(0).getName()),
                () -> assertNull(amenityList.get(0).getParent()),
                () -> assertEquals("Kuhinja", amenityList.get(1).getName()),
                () -> assertNull(amenityList.get(1).getParent()),
                () -> assertEquals("Sampon", amenityList.get(2).getName()),
                () -> assertEquals("Kupatilo", amenityList.get(2).getParent().getName()));

    }

    @Test
    @DatabaseSetup("classpath:amenity/amenityEmpty.xml")
    void findAllEmptyTest() {
        // Given: amenities don't exist
        // When: findAll amenities
        List<Amenity> amenityList = amenityService.getAmenities();
        // Then: return empty list
        assertTrue(amenityList.isEmpty());
    }

    @Test
    @DatabaseSetup("classpath:amenity/amenitySampleData.xml")
    @ExpectedDatabase(value = "classpath:amenity/amenityEmpty.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void deleteAllTest() {
        // Given: amenities exists
        // When: deleteAll amenities
        amenityService.deleteAllAmenity();
        // Then: amenities should not be present

    }
}
