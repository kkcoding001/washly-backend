
package com.washly.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.washly.backend.dto.StoreRequest;
import com.washly.backend.dto.StoreResponse;
import com.washly.backend.service.StoreManagement;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/stores")
public class AdminStoreController {

    private final StoreManagement storeManagement;

    public AdminStoreController(StoreManagement storeManagement) {
        this.storeManagement = storeManagement;
    }

    // Create a new store
    @PostMapping
    public ResponseEntity<StoreResponse> createStore(
            @Valid @RequestBody StoreRequest request) {

        StoreResponse response = storeManagement.createStore(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all stores, including inactive ones
    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStores() {
        return ResponseEntity.ok(storeManagement.getAllStores());
    }

    // Update store details
    @PutMapping("/{id}")
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable Long id,
            @Valid @RequestBody StoreRequest request) {

        return ResponseEntity.ok(
                storeManagement.updateStore(id, request));
    }

    // Deactivate a store
    @PatchMapping("/{id}/disable")
    public ResponseEntity<StoreResponse> deactivateStore(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                storeManagement.deactivateStore(id));
    }

    // Reactivate a store
    @PatchMapping("/{id}/activate")
    public ResponseEntity<StoreResponse> activateStore(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                storeManagement.activateStore(id));
    }
}
