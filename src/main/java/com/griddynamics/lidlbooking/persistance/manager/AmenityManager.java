package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.persistance.entity.AmenityEntity;
import com.griddynamics.lidlbooking.persistance.mapper.AmenityManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.AmenitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.*;
import static org.springframework.core.NestedExceptionUtils.getRootCause;

@Component
public class AmenityManager {

    private final AmenitiesRepository amenitiesRepository;

    private final AmenityManagerMapper mapper;

    @Autowired
    public AmenityManager(final AmenitiesRepository amenitiesRepository, final AmenityManagerMapper mapper) {
        this.amenitiesRepository = amenitiesRepository;
        this.mapper = mapper;
    }


    public Amenity findById(final Long id) {
        try {
            AmenityEntity entity = amenitiesRepository.findById(id).orElseThrow(
                    () -> new ElementNotFoundException(amenityIdDoesNotExist(id))
            );
            return mapper.amenityEntityToAmenity(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(AMENITY_ID_INVALID_FORMAT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(AMENITY_INVALID_PARENT);
        }
    }

    public List<Amenity> findAll() {
        List<AmenityEntity> entities = (List<AmenityEntity>) amenitiesRepository.findAll();

        return mapper.amenityEntityListToAmenityList(entities);
    }

    public Amenity save(final Amenity amenity) {
        try {
            AmenityEntity entity = amenitiesRepository.save(mapper.amenityToAmenityEntity(amenity));
            return mapper.amenityEntityToAmenity(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(AMENITY_ERROR + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new SQLException(AMENITY_ERROR + Objects.requireNonNull(getRootCause(e)).getMessage());
        }
    }

    public void deleteById(final Long id) {
        try {
            amenitiesRepository.deleteById(id);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(AMENITY_ID_INVALID_FORMAT);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException(amenityIdDoesNotExist(id));
        }
    }

    public void deleteAll() {
        amenitiesRepository.deleteAll();
    }

    public Amenity findByName(final String name) {
        try {
            AmenityEntity entity = amenitiesRepository.findByName(name).orElseThrow(
                    () -> new ElementNotFoundException(amenityNameDoesNotExist(name))
            );
            return mapper.amenityEntityToAmenity(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(AMENITY_NAME_INVALID_FORMAT);
        }
    }

    public List<Amenity> findAllByName(final List<String> names) {
        List<AmenityEntity> entityList = amenitiesRepository.findAllByNameIn(names);
        return mapper.amenityEntityListToAmenityList(entityList);

    }
}
