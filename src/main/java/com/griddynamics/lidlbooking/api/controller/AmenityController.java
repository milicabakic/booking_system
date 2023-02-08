package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.mapper.AmenityControllerMapper;
import com.griddynamics.lidlbooking.api.dto.AmenityDTO;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.domain.service.AmenityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/amenity")
@Tag(name = "Amenity Controller")
public class AmenityController {

    private final AmenityService amenityService;

    private final AmenityControllerMapper mapper;

    @Autowired
    public AmenityController(final AmenityService amenityService, final AmenityControllerMapper mapper) {
        this.amenityService = amenityService;
        this.mapper = mapper;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find an amenity", description = "Find an amenity by provided ID")
    public AmenityDTO getAmenity(@PathVariable("id") @Parameter(name = "id", example = "1")
                                     final Long id) {
        Amenity amenity = amenityService.getAmenityById(id);

        return mapper.amenityToAmenityDTO(amenity);
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Find all amenities", description = "Find all stored amenities")
    public List<AmenityDTO> getAmenities() {

        List<Amenity> amenities = amenityService.getAmenities();

        return mapper.amenityListToAmenityDTOList(amenities);
    }

    @PostMapping(path = "/")
    @Operation(summary = "Create an amenity", description = "Create an amenity with provided data")
    public AmenityDTO createAmenity(@RequestBody @Valid final AmenityDTO amenityDTO) {

        Amenity amenity = mapper.amenityDTOToAmenity(amenityDTO);

        Amenity amenityResponse = amenityService.createAmenity(amenity);

        return mapper.amenityToAmenityDTO(amenityResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an amenity", description = "Delete an amenity by provided ID")
    public void deleteAmenity(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id) {
        amenityService.deleteAmenity(id);
    }

    @DeleteMapping(path = "/all")
    @Operation(summary = "Delete all amenities", description = "Delete all stored amenities")
    public void deleteAllAmenities() {
        amenityService.deleteAllAmenity();
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update an amenity", description = "Update an amenity with provided data")
    public AmenityDTO updateAmenity(@RequestBody @Valid final AmenityDTO amenityDTO,
                                    @PathVariable final Long id) {

        Amenity amenity = mapper.amenityDTOToAmenity(amenityDTO);

        Amenity amenityResponse = amenityService.updateAmenity(amenity, id);

        return mapper.amenityToAmenityDTO(amenityResponse);

    }
}
