
package com.washly.backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.washly.backend.dto.CreateStaffRequest;
import com.washly.backend.dto.UserResponse;
import com.washly.backend.entity.User;
import com.washly.backend.service.UserService;

import jakarta.validation.Valid;

// endpoint requires an administrator's JWT

@RestController
@RequestMapping("/api/admin/staff")
public class StaffController {

    private final UserService userService;

    public StaffController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createStaff(
            @Valid @RequestBody CreateStaffRequest request) {

        User staff = new User();
        staff.setName(request.getName().trim());
        staff.setEmail(request.getEmail().trim().toLowerCase());
        staff.setPhone(request.getPhone());
        staff.setPassword(request.getPassword());

        try {
            User createdStaff = userService.createStaff(staff);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(UserResponse.from(createdStaff));

        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", exception.getMessage()));
        }
    }
}
