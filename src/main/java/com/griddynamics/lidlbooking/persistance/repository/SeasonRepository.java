package com.griddynamics.lidlbooking.persistance.repository;

import com.griddynamics.lidlbooking.persistance.entity.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SeasonRepository extends JpaRepository<SeasonEntity, Long> {

    List<SeasonEntity> findByIdIn(Collection<Long> id);

    List<SeasonEntity> findByIdInAndStudiosId(Collection<Long> id, Long studioId);
}
