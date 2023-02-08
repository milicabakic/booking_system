package com.griddynamics.lidlbooking.persistance.manager;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import com.griddynamics.lidlbooking.domain.model.Season;
import com.griddynamics.lidlbooking.persistance.entity.SeasonEntity;
import com.griddynamics.lidlbooking.persistance.mapper.SeasonManagerMapper;
import com.griddynamics.lidlbooking.persistance.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static org.springframework.core.NestedExceptionUtils.getRootCause;

@Component
public class SeasonManager {

    private final SeasonRepository seasonRepository;
    private final SeasonManagerMapper mapper;


    @Autowired
    public SeasonManager(final SeasonRepository seasonRepository,
                         final SeasonManagerMapper mapper) {
        this.seasonRepository = seasonRepository;
        this.mapper = mapper;
    }

    public Season save(final Season season) {
        try {
            SeasonEntity seasonEntity = mapper.seasonToSeasonEntity(season);
            SeasonEntity entity = seasonRepository.save(seasonEntity);
            return mapper.seasonEntityToSeason(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.SEASON_ERROR + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new SQLException(ErrorMessage.SEASON_ERROR + Objects.requireNonNull(getRootCause(e)).getMessage());
        }
    }

    public List<Season> findAll() {
        List<SeasonEntity> entityList = seasonRepository.findAll();
        return mapper.seasonEntityListToSeasonList(entityList);
    }

    public Season findById(final Long id) {
        try {
            SeasonEntity entity = seasonRepository.findById(id).orElseThrow(
                    () -> new ElementNotFoundException(ErrorMessage.seasonIdDoesNotExist(id))
            );
            return mapper.seasonEntityToSeason(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(ErrorMessage.SEASON_FORMAT);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException(ErrorMessage.SEASON_NULL);
        }
    }

    public boolean existsById(final List<Long> idList) {
        return seasonRepository.findByIdIn(idList).size() > 0;
    }

    public boolean checkSeasonsStudioMatch(final Long studioId, final List<Long> seasonIdList) {
        return seasonRepository.findByIdInAndStudiosId(seasonIdList, studioId).size() > 0;
    }
}
