package com.griddynamics.lidlbooking.api.controller;

import com.griddynamics.lidlbooking.api.dto.SeasonDto;
import com.griddynamics.lidlbooking.api.mapper.SeasonControllerMapper;
import com.griddynamics.lidlbooking.domain.model.Season;
import com.griddynamics.lidlbooking.domain.service.SeasonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/seasons")
@Tag(name = "Season API")
public class SeasonController {

    private final SeasonService seasonService;
    private final SeasonControllerMapper mapper;


    @Autowired
    public SeasonController(final SeasonService seasonService, final SeasonControllerMapper mapper) {
        this.seasonService = seasonService;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Find all seasons", description = "Find all seasons")
    public List<SeasonDto> findAll() {
        List<Season> seasonList = seasonService.findAll();
        return mapper.seasonListToSeasonDtoList(seasonList);
    }

    @PostMapping
    @Operation(summary = "Create a season", description = "Create a season with provided data")
    public SeasonDto createSeason(@Valid @RequestBody final SeasonDto seasonDto) {
        Season season = mapper.seasonDtoToSeason(seasonDto);
        return mapper.seasonToSeasonDto(seasonService.create(season));
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update a season", description = "Update a season with provided data")
    public SeasonDto updateSeason(@PathVariable("id") final Long id,
                                  @Valid @RequestBody final SeasonDto seasonDto) {
        Season season = mapper.seasonDtoToSeason(seasonDto);
        return mapper.seasonToSeasonDto(seasonService.update(id, season));
    }

    @PostMapping(path = "{seasonId}/studio/{studioId}")
    @Operation(summary = "Add a studio to a season", description = "Add a studio to a season with provided IDs")
    public SeasonDto matchSeasonStudio(@PathVariable final Long seasonId,
                                       @PathVariable final Long studioId) {
        return mapper.seasonToSeasonDto(seasonService.matchSeasonStudio(seasonId, studioId));
    }

}
