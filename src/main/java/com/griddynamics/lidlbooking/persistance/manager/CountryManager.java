package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.domain.model.Country;
import com.griddynamics.lidlbooking.persistance.entity.CountryEntity;
import com.griddynamics.lidlbooking.persistance.mapper.CountryManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.COUNTRY_ID_INVALID;
import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.countryIso3DoesNotExist;

@Component
public class CountryManager {

    private final CountryRepository countryRepository;

    private final CountryManagerMapper mapper;

    @Autowired
    public CountryManager(final CountryRepository countryRepository, final CountryManagerMapper mapper) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    public void deleteAll() {
        countryRepository.deleteAll();
    }

    public Country findById(final String iso3) {
        try {
            CountryEntity entity = countryRepository.findById(iso3).orElseThrow(
                    () -> new ElementNotFoundException(countryIso3DoesNotExist(iso3))

            );
            return mapper.countryEntityToCountry(entity);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(COUNTRY_ID_INVALID);
        }
    }
}
