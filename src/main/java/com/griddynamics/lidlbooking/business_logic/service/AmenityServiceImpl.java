package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.domain.model.structure.AmenityStructure;
import com.griddynamics.lidlbooking.domain.service.AmenityService;
import com.griddynamics.lidlbooking.persistance.manager.AmenityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.AMENITY_NULL;

public class AmenityServiceImpl implements AmenityService {


    private final AmenityManager manager;

    @Autowired
    public AmenityServiceImpl(final AmenityManager manager) {
        this.manager = manager;
    }

    @Override
    public Amenity getAmenityById(final Long id) {

        return manager.findById(id);
    }

    @Override
    public Amenity getAmenityByName(final String name) {
        return manager.findByName(name);
    }

    @Override
    public List<Amenity> getAmenities() {

        return manager.findAll();
    }

    @Override
    public Amenity createAmenity(final Amenity amenity) {
        if (amenity == null) {
            throw new BadRequestException(AMENITY_NULL);
        }
        return Optional.ofNullable(amenity.getParent())
                .map((parent) -> {
                    Amenity parentEntity = manager.findById(amenity.getParent().getId());
                    amenity.setParent(new AmenityStructure(parentEntity.getId(), parentEntity.getName()));
                    return manager.save(amenity);
                })
                .orElseGet(() -> manager.save(amenity));
    }

    @Override
    public void deleteAmenity(final Long id) {
        manager.deleteById(id);
    }

    @Override
    public Amenity updateAmenity(final Amenity amenity, final Long id) {
        if (amenity == null) {
            throw new BadRequestException(AMENITY_NULL);
        }
        Amenity amenityEntity = manager.findById(id);
        amenityEntity.setName(amenity.getName());

        return Optional.ofNullable(amenity.getParent())
                .map((parent) -> {
                    Amenity parentEntity = manager.findById(amenity.getParent().getId());
                    amenityEntity.setParent(new AmenityStructure(parentEntity.getId(), parentEntity.getName()));
                    return manager.save(amenityEntity);
                })
                .orElseGet(() -> {
                    amenityEntity.setParent(null);
                    return manager.save(amenityEntity);
                });
    }

    @Override
    public void deleteAllAmenity() {
        manager.deleteAll();
    }

    @Override
    public List<Amenity> findAmenitiesByName(final List<String> names) {
        return manager.findAllByName(names);
    }
}
