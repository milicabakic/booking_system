package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.persistance.entity.StudioEntity;
import com.griddynamics.lidlbooking.persistance.mapper.StudioManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.*;
import static org.springframework.core.NestedExceptionUtils.getRootCause;

@Component
public class StudioManager {

    private final StudioRepository studioRepository;

    private final StudioManagerMapper mapper;

    @Autowired
    public StudioManager(final StudioRepository studioRepository, final StudioManagerMapper mapper) {
        this.studioRepository = studioRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Studio findById(final Long id) {
        try {
            StudioEntity entity = studioRepository.findById(id).orElseThrow(
                    () -> new ElementNotFoundException(studioIdDoesNotExist(id))
            );
            return mapper.studioEntityToStudio(entity);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(STUDIO_ID_INVALID_FORMAT);
        }

    }

    @Transactional(readOnly = true)
    public List<Studio> findAll() {
        List<StudioEntity> entities = (List<StudioEntity>) studioRepository.findAll();
        return mapper.studioEntityListToStudioList(entities);
    }

    public void deleteById(final Long id) {
        try {
            studioRepository.deleteById(id);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(STUDIO_ID_INVALID_FORMAT);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException(studioIdDoesNotExist(id));
        }
    }

    public void deleteAll() {
        studioRepository.deleteAll();
    }

    public Studio save(final Studio studio) {
        try {
            StudioEntity entity = mapper.studioToStudioEntity(studio);
            entity = studioRepository.save(entity);
            return mapper.studioEntityToStudio(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(STUDIO_ERROR + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new SQLException(STUDIO_ERROR + Objects.requireNonNull(getRootCause(e)).getMessage());
        }

    }

    @Transactional
    public List<Studio> findAllForBooking() {
        List<StudioEntity> studioEntitiesForBooking = studioRepository.findAllForBooking();
        return mapper.studioEntityListToStudioList(studioEntitiesForBooking);
    }
}
