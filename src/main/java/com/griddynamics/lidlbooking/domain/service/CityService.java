package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.City;

public interface CityService {
    void deleteAll();

    City getCityById(Long id);
}
