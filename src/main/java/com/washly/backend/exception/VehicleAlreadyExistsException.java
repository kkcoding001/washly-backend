
package com.washly.backend.exception;

// Represents an attempt to register a vehicle registration number
// that is already being used.
public class VehicleAlreadyExistsException extends RuntimeException {

    public VehicleAlreadyExistsException(String message) {
        super(message);
    }
}
