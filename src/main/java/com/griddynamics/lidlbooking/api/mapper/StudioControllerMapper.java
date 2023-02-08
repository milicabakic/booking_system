package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.AmenityDTO;
import com.griddynamics.lidlbooking.api.dto.StudioDTO;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.domain.model.Studio;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudioControllerMapper {

    Studio studioDTOToStudio(StudioDTO studioDTO);

    StudioDTO studioToStudioDTO(Studio studio);

    List<StudioDTO> studioListToStudioDTOList(List<Studio> studios);

    List<Amenity> amenityDtoListToAmenityList(List<AmenityDTO> amenityDTOS);
}
