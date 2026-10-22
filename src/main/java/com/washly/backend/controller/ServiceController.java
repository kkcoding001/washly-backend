
package com.washly.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.washly.backend.dto.ServiceResponse;
import com.washly.backend.service.WashServiceManagement;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final WashServiceManagement washServiceManagement;

    public ServiceController(
            WashServiceManagement washServiceManagement) {
        this.washServiceManagement = washServiceManagement;
    }

    // Get active wash services for customers
    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getActiveServices() {
        return ResponseEntity.ok(
                washServiceManagement.getActiveServices());
    }
}
