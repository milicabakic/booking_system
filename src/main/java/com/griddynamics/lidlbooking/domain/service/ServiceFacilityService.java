package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.ServiceFacility;

import java.util.List;

public interface ServiceFacilityService {
    ServiceFacility getFacilityById(Long id);

    List<ServiceFacility> getFacilities();

    void deleteFacility(Long id);

    void deleteAllFacility();

    ServiceFacility createFacility(ServiceFacility facility);

    ServiceFacility updateFacility(ServiceFacility facility, Long id);
}
