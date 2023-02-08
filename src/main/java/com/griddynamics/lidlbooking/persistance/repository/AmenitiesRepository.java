package com.griddynamics.lidlbooking.persistance.repository;

import com.griddynamics.lidlbooking.persistance.entity.AmenityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmenitiesRepository extends CrudRepository<AmenityEntity, Long> {
    Optional<AmenityEntity> findByName(String name);

    List<AmenityEntity> findAllByNameIn(List<String> name);
}
