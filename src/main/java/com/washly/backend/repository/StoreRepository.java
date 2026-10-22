
package com.washly.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.washly.backend.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByActiveTrueOrderByNameAsc(); // Lists active stores for customers

    List<Store> findAllByOrderByNameAsc(); // Lists all stores, including inactive ones, for admins

    List<Store> findByCityIgnoreCaseAndActiveTrueOrderByNameAsc( // Finds active stores in a city
            String city);

    List<Store> findByAreaIgnoreCaseAndActiveTrueOrderByNameAsc( // Finds active stores in an area
            String area);
}
