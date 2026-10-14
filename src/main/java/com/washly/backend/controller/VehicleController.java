
package com.washly.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.washly.backend.dto.VehicleRequest;
import com.washly.backend.dto.VehicleResponse;
import com.washly.backend.entity.Vehicle;
import com.washly.backend.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // POST /api/customer/vehicles
    // Adds a new vehicle for the authenticated customer
    @PostMapping
    public ResponseEntity<VehicleResponse> addVehicle(
            Authentication authentication,
            @Valid @RequestBody VehicleRequest request) {
        
            // Get the logged-in customer's email from the authentication context
            // and pass it with the vehicle details to the service
        	Vehicle vehicle = vehicleService.addVehicle(
                    authentication.getName(), request);

            // Return HTTP 201 Created with the vehicle response as JSON
            // VehicleResponse prevents internal entity details from being exposed
        	return ResponseEntity.status(HttpStatus.CREATED)
                    .body(VehicleResponse.from(vehicle));

    }

    
    // GET /api/customer/vehicles
    // Retrieves all vehicles belonging to the authenticated customer
	@GetMapping
	public ResponseEntity<List<VehicleResponse>> getVehicles(
	        Authentication authentication) {
	
	    // Retrieve vehicles belonging to the logged-in customer
	    // Convert each Vehicle entity into a VehicleResponse DTO
	    List<VehicleResponse> vehicles = vehicleService
	            .getVehicles(authentication.getName())
	            .stream()
	            .map(VehicleResponse::from)
	            .toList();
	
	    // Return HTTP 200 OK with the list of vehicles
	    // Returns an empty list if the customer has no vehicles
	    return ResponseEntity.ok(vehicles);
	}


    // GET /api/customer/vehicles/{id}
    // Retrieves one vehicle by ID, provided it belongs to the customer
	@GetMapping("/{id}")
	public ResponseEntity<VehicleResponse> getVehicle(
	        Authentication authentication,
	        @PathVariable Long id) {
	
	    // Retrieve the vehicle only if it belongs to the logged-in customer
	    Vehicle vehicle = vehicleService.getVehicle(
	            authentication.getName(), id);
	
	    // Return HTTP 200 OK with the vehicle details
	    return ResponseEntity.ok(VehicleResponse.from(vehicle));
	}


    // PUT /api/customer/vehicles/{id}
    // Updates an existing vehicle belonging to the authenticated customer
	@PutMapping("/{id}")
	public ResponseEntity<VehicleResponse> updateVehicle(
	        Authentication authentication,
	        @PathVariable Long id,
	        @Valid @RequestBody VehicleRequest request) {
	
	    // Update the vehicle after checking customer ownership
	    // and validating the registration number
	    Vehicle vehicle = vehicleService.updateVehicle(
	            authentication.getName(), id, request);
	
	    // Return HTTP 200 OK with the updated vehicle details
	    return ResponseEntity.ok(VehicleResponse.from(vehicle));
	}


    // DELETE /api/customer/vehicles/{id}
    // Deletes a vehicle belonging to the authenticated customer
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVehicle(
	        Authentication authentication,
	        @PathVariable Long id) {
	
	    // Delete the vehicle only after verifying customer ownership
	    vehicleService.deleteVehicle(
	            authentication.getName(), id);
	
	    // Return HTTP 204 No Content after successful deletion
	    return ResponseEntity.noContent().build();
	}

}
