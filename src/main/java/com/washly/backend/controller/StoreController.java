
package com.washly.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.washly.backend.dto.StoreResponse;
import com.washly.backend.service.StoreManagement;

// let the customer app retrieve active stores and filter them by city or area

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreManagement storeManagement;

    public StoreController(StoreManagement storeManagement) {
        this.storeManagement = storeManagement;
    }

    // Get active stores, optionally filtered by city or area
    @GetMapping
    public ResponseEntity<List<StoreResponse>> getActiveStores(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String area) {

        if (city != null && !city.isBlank()) {
            return ResponseEntity.ok(
                    storeManagement.getActiveStoresByCity(city));
        }

        if (area != null && !area.isBlank()) {
            return ResponseEntity.ok(
                    storeManagement.getActiveStoresByArea(area));
        }

        return ResponseEntity.ok(storeManagement.getActiveStores());
    }
}
