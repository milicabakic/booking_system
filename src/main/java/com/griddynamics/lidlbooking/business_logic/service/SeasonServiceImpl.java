package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.model.Season;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
import com.griddynamics.lidlbooking.domain.service.SeasonService;
import com.griddynamics.lidlbooking.domain.service.StudioService;
import com.griddynamics.lidlbooking.persistance.manager.SeasonManager;

import java.time.LocalDate;
import java.util.List;

public class SeasonServiceImpl implements SeasonService {

    private SeasonManager manager;
    private JWTService jwtService;
    private BookingProviderService providerService;
    private StudioService studioService;


    public SeasonServiceImpl(final SeasonManager manager, final JWTService jwtService,
                             final BookingProviderService providerService,
                             final StudioService studioService) {
        this.manager = manager;
        this.jwtService = jwtService;
        this.providerService = providerService;
        this.studioService = studioService;
    }

    @Override
    public Season create(final Season season) {
        BookingProvider seasonCreator = getRequestSeasonCreator();
        validateSeason(season);
        season.setCreator(seasonCreator);
        return save(season);
    }

    @Override
    public List<Season> findAll() {
        return manager.findAll();
    }

    @Override
    public Season update(final Long id, final Season editedSeason) {
        validateSeason(editedSeason);
        Season season = findById(id);
        validateSeasonCreator(season);
        editSeasonAttributes(season, editedSeason);
        return save(season);
    }

    @Override
    public Season findById(final Long id) {
        return manager.findById(id);
    }

    @Override
    public Season matchSeasonStudio(final Long idSeason, final Long idStudio) {
        Season season = findById(idSeason);
        Studio studio = studioService.findById(idStudio);
        validateSeasonStudio(season, studio);
        studio.setSeasons(addSeasonToStudio(season, studio));
//        season.getStudioList().add(studio);
//        studio.getSeasonList().add(season);
        studioService.updateStudio(studio, idStudio);
        return save(season);
    }

    @Override
    public boolean existsById(final List<Long> idList) {
        if (idList == null) {
           throw new BadRequestException(ErrorMessage.SEASON_FORMAT);
        }
        return manager.existsById(idList);
    }

    @Override
    public boolean checkSeasonsStudioMatch(final Long studioId, final List<Long> seasonIdList) {
        if (seasonIdList == null || studioId == null) {
            throw new BadRequestException(ErrorMessage.SEASON_FORMAT);
        }
        return manager.checkSeasonsStudioMatch(studioId, seasonIdList);
    }

    private Season save(final Season season) {
        return manager.save(season);
    }

    private void validateSeason(final Season season) {
        if (season == null) {
            throw new BadRequestException(ErrorMessage.SEASON_SAVE);
        }
    }

    private void validateSeasonCreator(final Season season) {
        if (!checkCreatorsMatching(season.getCreator(), getRequestSeasonCreator())) {
            throw new BadRequestException(ErrorMessage.SEASON_VALIDATION);
        }
    }

    private void validateSeasonStudio(final Season season, final Studio studio) {
        if (!checkCreatorsMatching(season.getCreator(), studio.getBookingProvider())) {
            throw new BadRequestException(ErrorMessage.CREATORS_MISMATCH);
        }
    }

    private boolean checkCreatorsMatching(final BookingProvider seasonCreator, final BookingProvider requestCreator) {
        return seasonCreator.getId() == requestCreator.getId();
    }

    private BookingProvider getRequestSeasonCreator() {
        String providerUsername = jwtService.whoAmI();
        return providerService.findBookingProviderByUsername(providerUsername);
    }

    private void editSeasonAttributes(final Season season, final Season editedSeason) {
        season.setName(editedSeason.getName());
        season.setFromDate(editedSeason.getFromDate());
        season.setToDate(editedSeason.getToDate());
        season.setPricePerNight(editedSeason.getPricePerNight());
    }

    private List<Season> addSeasonToStudio(final Season season, final Studio studio) {
        List<Season> seasonList = studio.getSeasons();
        checkOverlappingSeasons(seasonList, season);
        seasonList.add(season);
        return seasonList;
    }

    private void checkOverlappingSeasons(final List<Season> seasonList, final Season checkSeason) {
        for (Season season : seasonList) {
            if (isOverlapping(season.getFromDate(), season.getToDate(),
                    checkSeason.getFromDate(), checkSeason.getToDate())) {
                throw new BadRequestException(ErrorMessage.SEASONS_OVERLAP);
            }
        }
    }

    private boolean isOverlapping(final LocalDate start1, final LocalDate end1,
                                  final LocalDate start2, final LocalDate end2) {
        return start1.compareTo(end2) <= 0 && end1.compareTo(start2) >= 0;
    }

}
