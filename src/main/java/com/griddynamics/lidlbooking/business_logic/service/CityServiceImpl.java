package com.griddynamics.lidlbooking.business_logic.service;


import com.griddynamics.lidlbooking.domain.model.City;
import com.griddynamics.lidlbooking.domain.service.CityService;
import com.griddynamics.lidlbooking.persistance.manager.CityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class CityServiceImpl implements CityService {

    private final CityManager manager;

    @Autowired
    public CityServiceImpl(final CityManager manager) {
        this.manager = manager;
    }

    @Override
    public void deleteAll() {
        manager.deleteAll();
    }

    @Override
    public City getCityById(final Long id) {

        return manager.findById(id);
    }


}
