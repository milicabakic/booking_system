package com.griddynamics.lidlbooking.crud.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.griddynamics.lidlbooking.business_logic.service.JWTService;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.model.Season;
import com.griddynamics.lidlbooking.domain.service.SeasonService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.Random.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SeasonServiceTest {

    @Autowired
    @InjectMocks
    private SeasonService service;

    @Mock
    private JWTService jwtService;


    @BeforeEach
    void setUp() {
        when(jwtService.whoAmI()).thenReturn("nikola");
    }

    @Nested
    class Create {
        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        @ExpectedDatabase(value = "classpath:season/seasonCreate.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createSeasonTest() {
            // Given: season
            // When: createSeason
            BookingProvider provider = new BookingProvider();
            provider.setId(1L);
            Season season = new Season(0L, "Name3", LocalDate.of(2023, 10, 01),
                    LocalDate.of(2023, 12, 31), 12, provider);

            service.create(season);
            // Then: season should be created
        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        @ExpectedDatabase(value = "classpath:season/seasonSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void createSeasonNullTest() {

            // Given: null
            // When: createSeason
            assertThrows(BadRequestException.class, () -> service.create(null));
            // Then: throw error
        }

        @Test
        @Disabled
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        @ExpectedDatabase(value = "classpath:season/seasonMatchStudio.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void matchSeasonStudioTest() {
            // Given: season ID and studio ID
            // When: matchSeasonStudio
           service.matchSeasonStudio(2L, 1L);
            // Then: season should be matched with studio
        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        @ExpectedDatabase(value = "classpath:season/seasonSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void matchSeasonStudioNullTest() {

            // Given: null
            // When: matchSeasonStudio
            assertThrows(BadRequestException.class, () -> service.matchSeasonStudio(null, null));
            // Then: throw error
        }
    }

    @Nested
    class Read {
        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void findByIdTest() {
            // Given: season exist
            // When: findById
            Season season = service.findById(1L);

            // Then: return season
            assertAll(() -> assertEquals(1, season.getId()),
                    () -> assertEquals("Name1", season.getName()),
                    () -> assertEquals(LocalDate.of(2023, 01, 01), season.getFromDate()),
                    () -> assertEquals(LocalDate.of(2023, 03, 31), season.getToDate()),
                    () -> assertEquals(1, season.getCreator().getId()));
        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void findByIdInvalidIdTest() {
            // Given: season doesn't exist
            // When: findById
            // Then: throw error
            assertThrows(ElementNotFoundException.class, () -> service.findById(-1L));
        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void findByIdNullTest() {
            // Given: null
            // When: findById
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.findById(null));

        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void findAllTest() {
            // Given: seasons exist
            // When: findAll
            List<Season> list = service.findAll();
            // Then: return seasons
            assertEquals(2, list.size());

        }

        @Test
        @DatabaseSetup("classpath:season/seasonEmpty.xml")
        void findAllEmptyTest() {
            // Given: seasons don't exist
            // When: findAll
            List<Season> list = service.findAll();
            // Then: return empty
            assertEquals(0, list.size());

        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void existsByIdTest() {
            // Given: season id list
            // When: existsById
            List<Long> seasonIdList = new ArrayList<>();
            seasonIdList.add(1L);
            seasonIdList.add(2L);

            // Then: return season
            assertEquals(true, service.existsById(seasonIdList));
        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void existsByIdInvalidIdTest() {
            // Given: season id list invalid
            // When: existsById

            List<Long> seasonIdList = new ArrayList<>();
            seasonIdList.add(-1L);
            seasonIdList.add(-2L);

            // Then: return 0
            assertEquals(false, service.existsById(seasonIdList));
        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void existsByIdNullTest() {
            // Given: null
            // When: existsById
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.existsById(null));

        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void existsByIdEmptyTest() {
            // Given: empty list
            // When: existsById
            // Then: return 0
            assertEquals(false, service.existsById(new ArrayList<>()));

        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void checkSeasonStudioMatchTest() {
            // Given: season id list invalid
            // When: existsById
            List<Long> seasonIdList = new ArrayList<>();
            seasonIdList.add(1L);

            // Then: return 0
            assertEquals(true, service.checkSeasonsStudioMatch(1L, seasonIdList));

        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void checkSeasonStudioMatchNullTest() {
            // Given: null
            // When: existsById
            // Then: throw error
            assertThrows(BadRequestException.class, () -> service.checkSeasonsStudioMatch(null, null));

        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        void checkSeasonStudioMatchEmptyTest() {
            // Given: empty list
            // When: existsById
            // Then: return 0
            assertEquals(false, service.checkSeasonsStudioMatch(1L, new ArrayList<>()));

        }

    }

    @Nested
    class Update {
        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        @ExpectedDatabase(value = "classpath:season/seasonUpdate.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateSeasonTest() {
            // Given: season
            // When: update
            BookingProvider provider = new BookingProvider();
            provider.setId(1L);
            Season season = new Season(0L, "Name1 updated", LocalDate.of(2023, 01, 01),
                    LocalDate.of(2023, 03, 31), 12, provider);


            service.update(1L, season);
            // Then: season should be updated
        }


        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        @ExpectedDatabase(value = "classpath:season/seasonSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateSeasonInvalidIdTest() {

            // Given: season, invalid id
            // When: update
            BookingProvider provider = new BookingProvider();
            provider.setId(1L);
            Season season = new Season(0L, "Name1 updated", LocalDate.of(2023, 10, 01),
                    LocalDate.of(2023, 12, 31), 12, provider);

            assertThrows(ElementNotFoundException.class, () -> service.update(-1L, season));
            // Then: throw error
        }

        @Test
        @DatabaseSetup("classpath:season/seasonSampleData.xml")
        @ExpectedDatabase(value = "classpath:season/seasonSampleData.xml",
                assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
        void updateSeasonNullTest() {

            // Given: null
            // When: update
            assertThrows(BadRequestException.class, () -> service.update(1L, null));
            // Then: throw error
        }
    }
}
