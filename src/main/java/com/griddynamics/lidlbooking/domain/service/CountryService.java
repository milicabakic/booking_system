package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.Country;

public interface CountryService {
    void deleteAll();

    Country getCountryById(String iso3);
}
