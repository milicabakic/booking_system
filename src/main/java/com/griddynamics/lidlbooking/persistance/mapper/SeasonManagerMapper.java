package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.Season;
import com.griddynamics.lidlbooking.persistance.entity.SeasonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonManagerMapper {

    Season seasonEntityToSeason(SeasonEntity seasonEntity);

    SeasonEntity seasonToSeasonEntity(Season season);

    List<Season> seasonEntityListToSeasonList(List<SeasonEntity> seasonEntityList);

}
