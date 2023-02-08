package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.domain.service.CityService;
import com.griddynamics.lidlbooking.domain.service.CountryService;
import com.griddynamics.lidlbooking.imports.Reader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/")
@Tag(name = "CountryReader API")
public class ReaderController {

    private final CountryService countryService;
    private final CityService cityService;
    private final Reader reader;


    @Autowired
    public ReaderController(final CountryService countryService, final Reader reader, final CityService cityService) {
        this.countryService = countryService;
        this.reader = reader;
        this.cityService = cityService;
    }


    @GetMapping(path = "/read")
    @Operation(summary = "Start reading", description = "Start reading country data")
    public void startReader() throws IOException {
        final String path = "src/main/resources/worldCities.csv";
        reader.setFile(path);
        reader.read();
    }

    @GetMapping(path = "/remove")
    @Operation(summary = "Delete all", description = "Delete all read country data")
    public void remove() {
        cityService.deleteAll();
        countryService.deleteAll();
    }
}
