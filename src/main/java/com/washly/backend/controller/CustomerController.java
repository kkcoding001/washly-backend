
package com.washly.backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.washly.backend.dto.UpdateProfileRequest;
import com.washly.backend.dto.UserResponse;
import com.washly.backend.entity.User;
import com.washly.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final UserService userService;

    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            Authentication authentication) { // supplied by Spring Security after your JWT filter verifies the token

        try {
            User user = userService.getProfile(
                    authentication.getName()); // returns only the authenticated user's email

            return ResponseEntity.ok(UserResponse.from(user));

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request) {

        try {
            User user = userService.updateProfile(
                    authentication.getName(),
                    request.getName(),
                    request.getPhone());

            return ResponseEntity.ok(UserResponse.from(user));

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
    }
}
