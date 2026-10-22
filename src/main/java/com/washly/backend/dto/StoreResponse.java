
package com.washly.backend.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;


//{
//	  "id": 1,
//	  "name": "Bopal Car Wash",
//	  "address": "Bopal Main Road",
//	  "area": "Bopal",
//	  "city": "Ahmedabad",
//	  "postalCode": "380058",
//	  "phone": "9876543210",
//	  "openingTime": "09:00:00",
//	  "closingTime": "20:00:00",
//	  "latitude": 23.0395,
//	  "longitude": 72.4652,
//	  "active": true
//}

public class StoreResponse {

    private Long id;
    private String name;
    private String address;
    private String area;
    private String city;
    private String postalCode;
    private String phone;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Double latitude;
    private Double longitude;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StoreResponse() {
    }

    public StoreResponse(
            Long id,
            String name,
            String address,
            String area,
            String city,
            String postalCode,
            String phone,
            LocalTime openingTime,
            LocalTime closingTime,
            Double latitude,
            Double longitude,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.area = area;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
