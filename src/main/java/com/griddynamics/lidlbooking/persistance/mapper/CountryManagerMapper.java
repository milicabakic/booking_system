package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.Country;
import com.griddynamics.lidlbooking.persistance.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryManagerMapper {

    Country countryEntityToCountry(CountryEntity entity);

    CountryEntity countryToCountryEntity(Country country);

}
