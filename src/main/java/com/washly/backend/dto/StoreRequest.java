
package com.washly.backend.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StoreRequest {

    @NotBlank(message = "Store name is required")
    @Size(max = 100, message = "Store name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Store address is required")
    @Size(max = 255, message = "Store address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Area is required")
    @Size(max = 100, message = "Area cannot exceed 100 characters")
    private String area;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "\\d{6}", message = "Postal code must contain 6 digits")
    private String postalCode;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "[6-9]\\d{9}",
            message = "Enter a valid 10-digit Indian mobile number")
    private String phone;

    @NotNull(message = "Opening time is required")
    private LocalTime openingTime;

    @NotNull(message = "Closing time is required")
    private LocalTime closingTime;

    private Double latitude;

    private Double longitude;

    public StoreRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
