package com.griddynamics.lidlbooking.persistance.repository;

import com.griddynamics.lidlbooking.persistance.entity.BookingProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingProviderRepository extends JpaRepository<BookingProviderEntity, Long> {

    Optional<BookingProviderEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByIdAndUsername(Long id, String username);
}
