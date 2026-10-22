
package com.washly.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.washly.backend.dto.ServiceRequest;
import com.washly.backend.dto.ServiceResponse;
import com.washly.backend.entity.WashService;
import com.washly.backend.repository.WashServiceRepository;

@Service
public class WashServiceManagement {

    private final WashServiceRepository washServiceRepository;

    public WashServiceManagement(WashServiceRepository washServiceRepository) {
        this.washServiceRepository = washServiceRepository;
    }

    // Create a new wash service
    public ServiceResponse createService(ServiceRequest request) {
        String serviceName = request.getName().trim();

        if (washServiceRepository.existsByNameIgnoreCase(serviceName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A service with this name already exists"
            );
        }

        WashService service = new WashService();
        service.setName(serviceName);
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setActive(true);

        WashService savedService = washServiceRepository.save(service);

        return toResponse(savedService);
    }

    // Get all services, including inactive services (admin)
    public List<ServiceResponse> getAllServices() {
        return washServiceRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Get active services only (customer app)
    public List<ServiceResponse> getActiveServices() {
        return washServiceRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Update service details without changing its active status
    public ServiceResponse updateService(Long id, ServiceRequest request) {
        WashService service = findServiceById(id);
        String serviceName = request.getName().trim();

        boolean nameChanged =
                !service.getName().equalsIgnoreCase(serviceName);

        if (nameChanged
                && washServiceRepository.existsByNameIgnoreCase(serviceName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A service with this name already exists"
            );
        }

        service.setName(serviceName);
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());

        WashService updatedService = washServiceRepository.save(service);

        return toResponse(updatedService);
    }

    // Deactivate a service without deleting its database record
    public ServiceResponse deactivateService(Long id) {
        WashService service = findServiceById(id);
        service.setActive(false);

        return toResponse(washServiceRepository.save(service));
    }

    // Reactivate a previously deactivated service
    public ServiceResponse activateService(Long id) {
        WashService service = findServiceById(id);
        service.setActive(true);

        return toResponse(washServiceRepository.save(service));
    }

    private WashService findServiceById(Long id) {
        return washServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Wash service not found with ID: " + id
                ));
    }

    private ServiceResponse toResponse(WashService service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes(),
                service.isActive(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }
}
