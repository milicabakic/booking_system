package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import com.griddynamics.lidlbooking.persistance.entity.ServiceFacilityEntity;
import com.griddynamics.lidlbooking.persistance.mapper.ServiceFacilityManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.ServiceFacilityRepository;
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
public class ServiceFacilityManager {

    private final ServiceFacilityRepository serviceFacilityRepository;

    private final ServiceFacilityManagerMapper mapper;

    @Autowired
    public ServiceFacilityManager(final ServiceFacilityRepository serviceFacilityRepository,
                                  final ServiceFacilityManagerMapper mapper) {
        this.serviceFacilityRepository = serviceFacilityRepository;
        this.mapper = mapper;
    }

    public ServiceFacility findById(final Long id) {
        try {
            ServiceFacilityEntity entity = serviceFacilityRepository.findById(id).orElseThrow(
                    () -> new ElementNotFoundException(serviceFacilityIdDoesNotExist(id))
            );
            return mapper.serviceFacilityEntityToServiceFacility(entity);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(SERVICE_FACILITY_ID_INVALID_FORMAT);
        }
    }

    public List<ServiceFacility> findAll() {
        List<ServiceFacilityEntity> entities = (List<ServiceFacilityEntity>) serviceFacilityRepository.findAll();

        return mapper.serviceFacilityEntityListToServiceFacilityList(entities);
    }

    public void deleteById(final Long id) {
        try {
            serviceFacilityRepository.deleteById(id);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(SERVICE_FACILITY_ID_INVALID_FORMAT);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException(serviceFacilityIdDoesNotExist(id));
        }
    }

    public void deleteAll() {
        serviceFacilityRepository.deleteAll();
    }

    public ServiceFacility save(final ServiceFacility facility) {
        try {
            ServiceFacilityEntity entity = mapper.serviceFacilityToServiceFacilityEntity(facility);
            entity = serviceFacilityRepository.save(entity);
            return mapper.serviceFacilityEntityToServiceFacility(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(SERVICE_FACILITY_ERROR + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new SQLException(SERVICE_FACILITY_ERROR + Objects.requireNonNull(getRootCause(e)).getMessage());
        }

    }

}
