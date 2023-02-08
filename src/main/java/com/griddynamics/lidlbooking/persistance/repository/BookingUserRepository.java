package com.griddynamics.lidlbooking.persistance.repository;

import com.griddynamics.lidlbooking.persistance.entity.BookingUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingUserRepository extends JpaRepository<BookingUserEntity, Long> {

    Optional<BookingUserEntity> findByUsername(String username);

    boolean existsByIdAndUsername(Long id, String username);

    boolean existsByUsername(String username);
}
