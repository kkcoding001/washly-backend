
package com.washly.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.washly.backend.dto.StoreRequest;
import com.washly.backend.dto.StoreResponse;
import com.washly.backend.entity.Store;
import com.washly.backend.repository.StoreRepository;

@Service
public class StoreManagement {

    private final StoreRepository storeRepository;

    public StoreManagement(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // Create a new store
    public StoreResponse createStore(StoreRequest request) {
        validateStoreRequest(request);

        Store store = new Store();
        applyRequest(store, request);
        store.setActive(true);

        return toResponse(storeRepository.save(store));
    }

    // Get all stores, including inactive stores (admin)
    public List<StoreResponse> getAllStores() {
        return storeRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Get active stores only (customer)
    public List<StoreResponse> getActiveStores() {
        return storeRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Get active stores by city (customer)
    public List<StoreResponse> getActiveStoresByCity(String city) {
        if (city == null || city.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "City is required");
        }

        return storeRepository
                .findByCityIgnoreCaseAndActiveTrueOrderByNameAsc(city.trim())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Get active stores by area (customer)
    public List<StoreResponse> getActiveStoresByArea(String area) {
        if (area == null || area.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Area is required");
        }

        return storeRepository
                .findByAreaIgnoreCaseAndActiveTrueOrderByNameAsc(area.trim())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Update store details without changing active status
    public StoreResponse updateStore(Long id, StoreRequest request) {
        validateStoreRequest(request);

        Store store = findStoreById(id);
        applyRequest(store, request);

        return toResponse(storeRepository.save(store));
    }

    // Deactivate a store without deleting it
    public StoreResponse deactivateStore(Long id) {
        Store store = findStoreById(id);
        store.setActive(false);

        return toResponse(storeRepository.save(store));
    }

    // Reactivate a store
    public StoreResponse activateStore(Long id) {
        Store store = findStoreById(id);
        store.setActive(true);

        return toResponse(storeRepository.save(store));
    }

    private void validateStoreRequest(StoreRequest request) {
        if (request.getOpeningTime() != null
                && request.getClosingTime() != null
                && !request.getOpeningTime().isBefore(request.getClosingTime())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Opening time must be earlier than closing time");
        }

        Double latitude = request.getLatitude();
        Double longitude = request.getLongitude();

        if (latitude != null && (latitude < -90 || latitude > 90)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Latitude must be between -90 and 90");
        }

        if (longitude != null && (longitude < -180 || longitude > 180)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Longitude must be between -180 and 180");
        }

        if ((latitude == null) != (longitude == null)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Latitude and longitude must either both be provided or both be omitted");
        }
    }

    private void applyRequest(Store store, StoreRequest request) {
        store.setName(request.getName().trim());
        store.setAddress(request.getAddress().trim());
        store.setArea(request.getArea().trim());
        store.setCity(request.getCity().trim());
        store.setPostalCode(request.getPostalCode().trim());
        store.setPhone(request.getPhone().trim());
        store.setOpeningTime(request.getOpeningTime());
        store.setClosingTime(request.getClosingTime());
        store.setLatitude(request.getLatitude());
        store.setLongitude(request.getLongitude());
    }

    private Store findStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Store not found with ID: " + id));
    }

    private StoreResponse toResponse(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getArea(),
                store.getCity(),
                store.getPostalCode(),
                store.getPhone(),
                store.getOpeningTime(),
                store.getClosingTime(),
                store.getLatitude(),
                store.getLongitude(),
                store.isActive(),
                store.getCreatedAt(),
                store.getUpdatedAt());
    }
}
