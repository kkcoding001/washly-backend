
package com.washly.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.washly.backend.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByOwnerId(Long ownerId); // retrieves vehicles belonging to a particular customer

    Optional<Vehicle> findByIdAndOwnerId(Long id, Long ownerId); // retrieves a vehicle only when it belongs to the specified customer

    boolean existsByRegistrationNumber(String registrationNumber); // checks whether a registration number is already registered
    
    // prevent a vehicle from being updated to a registration number already used by another vehicle
    boolean existsByRegistrationNumberAndIdNot(
            String registrationNumber, Long id);
}
