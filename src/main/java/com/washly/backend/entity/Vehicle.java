
package com.washly.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "vehicles",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_vehicle_registration_number",
            columnNames = "registration_number"
        )
    }
)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_type", nullable = false, length = 20)
    private String vehicleType = "Car";

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(
        name = "registration_number",
        nullable = false,
        length = 20
    )
    private String registrationNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Vehicle() {
    }

    @PrePersist
    @PreUpdate
    public void normalizeFields() {
        vehicleType = "Car";

        if (brand != null) {
            brand = brand.trim();
        }

        if (model != null) {
            model = model.trim();
        }

        if (registrationNumber != null) {
            registrationNumber =
                registrationNumber.trim().toUpperCase();
        }
    }

    public Long getId() {
        return id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = "Car";
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
