package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.City;
import com.griddynamics.lidlbooking.persistance.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityManagerMapper {

    City cityEntityToCity(CityEntity entity);

    CityEntity cityToCityEntity(City city);

}
