package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.SeasonDto;
import com.griddynamics.lidlbooking.domain.model.Season;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonControllerMapper {

    SeasonDto seasonToSeasonDto(Season season);

    Season seasonDtoToSeason(SeasonDto seasonDto);

    List<SeasonDto> seasonListToSeasonDtoList(List<Season> seasonList);

}
