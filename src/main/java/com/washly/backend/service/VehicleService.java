
package com.washly.backend.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.washly.backend.dto.VehicleRequest;
import com.washly.backend.entity.User;
import com.washly.backend.entity.Vehicle;
import com.washly.backend.repository.UserRepository;
import com.washly.backend.repository.VehicleRepository;
import com.washly.backend.exception.CustomerUnavailableException;
import com.washly.backend.exception.VehicleAlreadyExistsException;
import com.washly.backend.exception.VehicleNotFoundException;

/**
 * Service layer responsible for vehicle-related operations
 * Handles validation, ownership checks, and database interactions
 */

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    private User getCustomer(String email) {

        // Normalize email before searching
        User user = userRepository.findByEmail(
                email.trim().toLowerCase(Locale.ROOT))
            .orElseThrow(() ->
                new CustomerUnavailableException("Customer account unavailable"));

        // Prevent inactive users from performing operations
        if (!user.isActive()) {
            throw new CustomerUnavailableException(
                    "Customer account unavailable");
        }

        return user;
    }

    /**
     * Standardize vehicle registration number.
     *
     * Example:
     * " gj01ab1234 " -> "GJ01AB1234"
     */
    private String normalizeRegistration(String registrationNumber) {
        return registrationNumber.trim()
                .toUpperCase(Locale.ROOT);
    }

    
    /**
     * Add a new vehicle for the logged-in customer.
     *
     * Steps:
     * 1. Verify customer exists and is active
     * 2. Normalize registration number
     * 3. Ensure registration number is unique
     * 4. Create and save vehicle
     */
    @Transactional
    public Vehicle addVehicle(String email, VehicleRequest request) {
        User customer = getCustomer(email);

        // Normalize registration number
        String registration = normalizeRegistration(
                request.getRegistrationNumber());

        // Prevent duplicate registration numbers
        if (vehicleRepository.existsByRegistrationNumber(registration)) {
        	throw new VehicleAlreadyExistsException(
        	        "Registration number is already registered");
        }

        Vehicle vehicle = new Vehicle();
        
        vehicle.setVehicleType("Car");
        vehicle.setBrand(request.getBrand().trim());
        vehicle.setModel(request.getModel().trim());
        vehicle.setRegistrationNumber(registration);
        
        vehicle.setOwner(customer); // Link vehicle to customer

        return vehicleRepository.save(vehicle);
    }
    
    // Retrieve all vehicles owned by the logged-in customer
    @Transactional(readOnly = true)
    public List<Vehicle> getVehicles(String email) {
        User customer = getCustomer(email);
        return vehicleRepository.findByOwnerId(customer.getId());
    }

    // Retrieve a specific vehicle belonging to the customer
    @Transactional(readOnly = true)
    public Vehicle getVehicle(String email, Long vehicleId) {
        User customer = getCustomer(email);

        return vehicleRepository.findByIdAndOwnerId(
                vehicleId, customer.getId()) 
        		.orElseThrow(() ->
                new VehicleNotFoundException("Vehicle not found"));
    }

    
    /**
     * Update an existing vehicle.
     *
     * Steps:
     * 1. Verify ownership
     * 2. Normalize registration number
     * 3. Ensure registration number is unique
     * 4. Update vehicle details
     * 5. Save changes
     */
    @Transactional
    public Vehicle updateVehicle(
            String email, Long vehicleId, VehicleRequest request) {
    	
    	// Verify vehicle belongs to customer
        Vehicle vehicle = getVehicle(email, vehicleId);

        // Normalize registration number
        String registration = normalizeRegistration(
                request.getRegistrationNumber());

        // Check duplicate registration number
        // Exclude current vehicle using IdNot
        if (vehicleRepository.existsByRegistrationNumberAndIdNot(
                registration, vehicleId)) {
        	throw new VehicleAlreadyExistsException(
        	        "Registration number is already registered");
        }

        vehicle.setVehicleType("Car");
        vehicle.setBrand(request.getBrand().trim());
        vehicle.setModel(request.getModel().trim());
        vehicle.setRegistrationNumber(registration);

        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteVehicle(String email, Long vehicleId) {
        Vehicle vehicle = getVehicle(email, vehicleId);
        vehicleRepository.delete(vehicle);
    }
}
