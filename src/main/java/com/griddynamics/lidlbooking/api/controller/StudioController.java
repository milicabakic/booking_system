package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.StudioDTO;
import com.griddynamics.lidlbooking.api.dto.StudioClassificationDTO;
import com.griddynamics.lidlbooking.api.mapper.StudioControllerMapper;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.service.StudioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/studio")
@Tag(name = "Studio API")
public class StudioController {

    private final StudioService studioService;

    private final StudioControllerMapper mapper;

    @Autowired
    public StudioController(final StudioService studioService, final StudioControllerMapper mapper) {
        this.studioService = studioService;
        this.mapper = mapper;

    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find a studio", description = "Find a studio by provided ID")
    public StudioDTO getStudio(@PathVariable("id") @Parameter(name = "id", example = "1")
                                   final long id) {
        Studio studio = studioService.findById(id);
        return mapper.studioToStudioDTO(studio);
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Find all", description = "Find all studios")
    public List<StudioDTO> getStudios() {
        List<Studio> studios = studioService.findAll();
        return mapper.studioListToStudioDTOList(studios);
    }

    @PostMapping
    @Operation(summary = "Create a studio", description = "Create a studio with provided data")
    public StudioDTO createStudio(@Valid @RequestBody final StudioDTO studioDTO) {
        Studio studio = mapper.studioDTOToStudio(studioDTO);
        Studio studioResponse = studioService.createStudio(studio);
        return mapper.studioToStudioDTO(studioResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a studio", description = "Delete a studio by provided ID")
    public void deleteStudio(@PathVariable("id") @Parameter(name = "id", example = "1") final Long id) {
        studioService.deleteStudio(id);
    }

    @DeleteMapping(path = "/all")
    @Operation(summary = "Delete all", description = "Delete all studio data")
    public void deleteAll() {
        studioService.deleteAllStudios();
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update a studio", description = "Update a studio by provided ID and data")
    public StudioDTO updateStudio(@Valid @RequestBody final StudioDTO studioDTO, @PathVariable final long id) {
        Studio studio = mapper.studioDTOToStudio(studioDTO);
        Studio studioResponse = studioService.updateStudio(studio, id);
        return mapper.studioToStudioDTO(studioResponse);
    }

    @PutMapping(path = "/amenity/{id}")
    @Operation(summary = "Add Amenities to a Studio", description = "Update a studio by provided amenities. "
            + "Then update studio class.")
    public StudioDTO updateStudioAmenities(@PathVariable final Long id,
                                           @Valid @RequestBody final StudioClassificationDTO dto) {
        List<String> amenities = dto.getAmenity();
        Studio studio = studioService.updateStudioAmenities(id, amenities);
        return mapper.studioToStudioDTO(studio);
    }
}
