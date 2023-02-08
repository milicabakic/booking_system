package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.persistance.entity.StudioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudioManagerMapper {

    Studio studioEntityToStudio(StudioEntity studioEntity);

    List<Studio> studioEntityListToStudioList(List<StudioEntity> studioEntities);

    StudioEntity studioToStudioEntity(Studio studio);

}
