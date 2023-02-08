package com.griddynamics.lidlbooking.domain.service;

import com.griddynamics.lidlbooking.domain.model.Amenity;

import java.util.List;

public interface AmenityService {
    Amenity getAmenityById(Long id);

    Amenity getAmenityByName(String name);


    List<Amenity> getAmenities();

    Amenity createAmenity(Amenity amenity);

    void deleteAmenity(Long id);

    Amenity updateAmenity(Amenity amenity, Long id);

    void deleteAllAmenity();

    List<Amenity> findAmenitiesByName(List<String> names);
}
