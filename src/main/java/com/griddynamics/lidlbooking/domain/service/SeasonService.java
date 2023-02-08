package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.Season;

import java.util.List;

public interface SeasonService {

    Season create(Season season);

    List<Season> findAll();

    Season update(Long id, Season editedSeason);

    Season findById(Long id);

    Season matchSeasonStudio(Long idSeason, Long idStudio);

    boolean existsById(List<Long> idList);

    boolean checkSeasonsStudioMatch(Long studioId, List<Long> seasonIdList);

}
