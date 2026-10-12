
package com.washly.backend.dto;

import com.washly.backend.entity.Role;
import com.washly.backend.entity.User;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;

    public UserResponse(Long id, String name, String email,
                        String phone, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }
}
