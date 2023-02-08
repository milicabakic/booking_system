package com.griddynamics.lidlbooking.persistance.repository;

import com.griddynamics.lidlbooking.persistance.entity.BookingAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingAdminRepository extends JpaRepository<BookingAdminEntity, Long> {

    Optional<BookingAdminEntity> findByUsername(String username);

    boolean existsByIdAndUsername(Long id, String username);
}
