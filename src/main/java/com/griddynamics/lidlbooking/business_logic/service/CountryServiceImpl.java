package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.domain.model.Country;
import com.griddynamics.lidlbooking.domain.service.CountryService;
import com.griddynamics.lidlbooking.persistance.manager.CountryManager;
import org.springframework.beans.factory.annotation.Autowired;

public class CountryServiceImpl implements CountryService {

    private final CountryManager manager;

    @Autowired
    public CountryServiceImpl(final CountryManager manager) {
        this.manager = manager;
    }

    @Override
    public void deleteAll() {
        manager.deleteAll();
    }

    @Override
    public Country getCountryById(final String iso3) {

        return manager.findById(iso3);
    }


}
