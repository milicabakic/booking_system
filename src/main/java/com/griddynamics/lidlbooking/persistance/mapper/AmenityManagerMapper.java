package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.persistance.entity.AmenityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AmenityManagerMapper {


    Amenity amenityEntityToAmenity(AmenityEntity amenityEntity);

    List<Amenity> amenityEntityListToAmenityList(List<AmenityEntity> amenityEntities);

    AmenityEntity amenityToAmenityEntity(Amenity amenity);
}
