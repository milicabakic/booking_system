package com.griddynamics.lidlbooking.imports;

import com.griddynamics.lidlbooking.persistance.entity.CityEntity;
import com.griddynamics.lidlbooking.persistance.entity.CountryEntity;
import com.griddynamics.lidlbooking.persistance.repository.CityRepository;
import com.griddynamics.lidlbooking.persistance.repository.CountryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:postgresql://localhost:5432/test",
        "reader.lines = 40"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
public class ReaderTest {
    private final String path = "src/test/resources/worldCities.csv";
    @Autowired
    private Reader reader;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    private Set<String> countryISO3 = new HashSet<>();

    @BeforeAll
    void setCountrySet() throws FileNotFoundException {
        countryRepository.deleteAll();
        cityRepository.deleteAll();
        FileInputStream inputStream = new FileInputStream( new File("src/test/resources/ISO3Countries"));
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()){
            String[] strings = scanner.nextLine().replaceAll("\"","")
                    .replaceAll(" ","").split(",");
            for (String s: strings) {
                countryISO3.add(s);
            }
        }
    }
    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        reader.setFile(path);
        reader.read();
        Thread.sleep(1500);

    }

    @AfterEach
    void cleanUp() {
        cityRepository.deleteAll();
        countryRepository.deleteAll();
    }

    @Test
    void countryReaderTest() {

        List<CountryEntity> countryEntityList = new ArrayList<>(
                (Collection<? extends CountryEntity>) countryRepository.findAll());

        assertEquals(236, countryEntityList.size());

        countryEntityList = countryEntityList.stream()
                .filter(countryEntity -> !countryISO3.contains(countryEntity.getIso3()))
                .collect(Collectors.toList());

        assertEquals(0, countryEntityList.size());

    }


    @Test
    void cityReaderTest() {

        List<CityEntity> cityEntityList = new ArrayList<>(
                (Collection<? extends CityEntity>) cityRepository.findAll());

        assertEquals(999, cityEntityList.size());

        cityEntityList = cityEntityList.stream()
                .filter(cityEntity -> !countryISO3.contains(cityEntity.getCountry().getIso3()))
                .collect(Collectors.toList());

        assertEquals(0, cityEntityList.size());


    }

}
