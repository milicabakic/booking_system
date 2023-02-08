package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.City;
import com.griddynamics.lidlbooking.persistance.entity.CityEntity;
import com.griddynamics.lidlbooking.persistance.mapper.CityManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.CITY_ID_INVALID;
import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.cityIdDoesNotExist;

@Component
public class CityManager {

    private final CityRepository cityRepository;

    private final CityManagerMapper mapper;

    @Autowired
    public CityManager(final CityRepository cityRepository, final CityManagerMapper mapper) {
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    public City findById(final Long id) {
        try {
            CityEntity entity = cityRepository.findById(id).orElseThrow(
                    () -> new ElementNotFoundException(cityIdDoesNotExist(id))
            );
            return mapper.cityEntityToCity(entity);

        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(CITY_ID_INVALID);
        }

    }

    public void deleteAll() {
        cityRepository.deleteAll();
    }
}
