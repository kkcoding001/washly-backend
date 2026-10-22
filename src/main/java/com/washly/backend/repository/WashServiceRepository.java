
package com.washly.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.washly.backend.entity.WashService;

public interface WashServiceRepository
        extends JpaRepository<WashService, Long> {

    Optional<WashService> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<WashService> findByActiveTrueOrderByNameAsc(); // Return active services alphabetically for customers

    List<WashService> findAllByOrderByNameAsc(); // Return all services, including inactive ones, for admin
}
