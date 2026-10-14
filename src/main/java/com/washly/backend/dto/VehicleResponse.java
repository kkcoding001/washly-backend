
package com.washly.backend.dto;

import com.washly.backend.entity.Vehicle;

public class VehicleResponse {

    private Long id;
    private String vehicleType;
    private String brand;
    private String model;
    private String registrationNumber;

    public VehicleResponse() {
    }

    public static VehicleResponse from(Vehicle vehicle) {
    	
        VehicleResponse response = new VehicleResponse();

        response.setId(vehicle.getId());
        response.setVehicleType(vehicle.getVehicleType());
        response.setBrand(vehicle.getBrand());
        response.setModel(vehicle.getModel());
        response.setRegistrationNumber(vehicle.getRegistrationNumber());

        return response;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
