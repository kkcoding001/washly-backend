
package com.washly.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/customer/test")
    public Map<String, String> customerTest() {
        return Map.of("message", "Customer access granted");
    }

    @GetMapping("/staff/test")
    public Map<String, String> staffTest() {
        return Map.of("message", "Staff access granted");
    }

    @GetMapping("/admin/test")
    public Map<String, String> adminTest() {
        return Map.of("message", "Admin access granted");
    }
}
