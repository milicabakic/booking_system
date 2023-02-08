package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.ServiceFacilityDTO;
import com.griddynamics.lidlbooking.api.mapper.ServiceFacilityControllerMapper;
import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import com.griddynamics.lidlbooking.domain.service.ServiceFacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/facility")
@Tag(name = "ServiceFacility API")
public class ServiceFacilityController {


    private final ServiceFacilityService serviceFacilityService;

    private final ServiceFacilityControllerMapper mapper;

    @Autowired
    public ServiceFacilityController(final ServiceFacilityService serviceFacilityService,
                                     final ServiceFacilityControllerMapper mapper) {
        this.serviceFacilityService = serviceFacilityService;
        this.mapper = mapper;

    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find a facility", description = "Find a facility with provided ID")
    public ServiceFacilityDTO getFacility(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id) {
        ServiceFacility response = serviceFacilityService.getFacilityById(id);
        return mapper.serviceFacilityToServiceFacilityDTO(response);
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Find all", description = "Find all facilities")
    public List<ServiceFacilityDTO> getFacilities() {
        List<ServiceFacility> serviceFacilityResponses = serviceFacilityService.getFacilities();

        return mapper.serviceFacilityListToServiceFacilityDTOList(serviceFacilityResponses);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a facility", description = "Delete a facility with provided ID")
    public void deleteFacility(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id) {
        serviceFacilityService.deleteFacility(id);
    }

    @DeleteMapping(path = "/all")
    public void deleteAllFacilities() {
        serviceFacilityService.deleteAllFacility();
    }

    @PostMapping(path = "/")
    @Operation(summary = "Create a facility", description = "Create a facility with provided data")
    public ServiceFacilityDTO createFacility(@Valid @RequestBody final ServiceFacilityDTO serviceFacilityDTO) {
        ServiceFacility facility = mapper.serviceFacilityDTOToServiceFacility(serviceFacilityDTO);
        ServiceFacility response = serviceFacilityService.createFacility(facility);
        return mapper.serviceFacilityToServiceFacilityDTO(response);
    }

    @PutMapping(path = "/{id}")
    public ServiceFacilityDTO updateFacility(@Valid @RequestBody final ServiceFacilityDTO serviceFacilityDTO,
                                             @PathVariable final Long id) {
        ServiceFacility facility = mapper.serviceFacilityDTOToServiceFacility(serviceFacilityDTO);
        ServiceFacility response = serviceFacilityService.updateFacility(facility, id);
        return mapper.serviceFacilityToServiceFacilityDTO(response);
    }

}
