package com.griddynamics.lidlbooking.persistance.repository;

import com.griddynamics.lidlbooking.persistance.entity.StudioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudioRepository extends CrudRepository<StudioEntity, Long> {

    @Query("SELECT studio FROM StudioEntity studio WHERE studio.seasons IS NOT EMPTY")
    List<StudioEntity> findAllForBooking();

    @Query("SELECT DISTINCT s FROM StudioEntity s LEFT JOIN FETCH s.amenities "
            + "WHERE s.id = :id")
    Optional<StudioEntity> findByIdFetch(@Param("id") Long id);

    @Query("SELECT DISTINCT s FROM StudioEntity s LEFT JOIN FETCH s.amenities")
    List<StudioEntity> findAllFetch();

}
