package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.AmenityDTO;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AmenityControllerMapper {

    Amenity amenityDTOToAmenity(AmenityDTO amenityDTO);

    List<AmenityDTO> amenityListToAmenityDTOList(List<Amenity> amenities);

    AmenityDTO amenityToAmenityDTO(Amenity amenity);

}
