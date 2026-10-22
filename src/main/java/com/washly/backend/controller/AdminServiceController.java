
package com.washly.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.washly.backend.dto.ServiceRequest;
import com.washly.backend.dto.ServiceResponse;
import com.washly.backend.service.WashServiceManagement;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/services")
public class AdminServiceController {

    private final WashServiceManagement washServiceManagement;

    public AdminServiceController(
            WashServiceManagement washServiceManagement) {
        this.washServiceManagement = washServiceManagement;
    }

    // Create a new service
    @PostMapping
    public ResponseEntity<ServiceResponse> createService(
            @Valid @RequestBody ServiceRequest request) {

        ServiceResponse response =
                washServiceManagement.createService(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all services, including inactive ones
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        return ResponseEntity.ok(
                washServiceManagement.getAllServices());
    }

    // Update service details
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequest request) {

        return ResponseEntity.ok(
                washServiceManagement.updateService(id, request));
    }

    // Deactivate a service
    @PatchMapping("/{id}/disable")
    public ResponseEntity<ServiceResponse> deactivateService(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                washServiceManagement.deactivateService(id));
    }

    // Reactivate a service
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ServiceResponse> activateService(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                washServiceManagement.activateService(id));
    }
}
