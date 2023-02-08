package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.Studio;

import java.util.List;

public interface StudioService {

    Studio findById(Long id);

    List<Studio> findAll();

    Studio createStudio(Studio studio);

    void deleteStudio(Long id);

    void deleteAllStudios();

    Studio updateStudio(Studio studio, Long id);

    Studio updateStudioAmenities(Long id, List<String> amenities);

    List<Studio> findAllForBooking();
}
