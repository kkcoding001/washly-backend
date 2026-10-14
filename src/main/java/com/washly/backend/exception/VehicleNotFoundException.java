
package com.washly.backend.exception;

// Represents a vehicle that does not exist or does not belong
// to the authenticated customer.
public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(String message) {
        super(message);
    }
}
