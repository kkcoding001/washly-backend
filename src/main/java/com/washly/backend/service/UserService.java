
package com.washly.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.washly.backend.entity.Role;
import com.washly.backend.entity.User;
import com.washly.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService( UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
 
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                    "Email is already registered");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.CUSTOMER);
        user.setActive(true);

        return userRepository.save(user);
    }
    
    
    public User authenticateUser(String email, String password) {

        User user = userRepository.findByEmail(
                email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid email or password"));

        // password verification
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(
                    "Invalid email or password");
        }

        if (!user.isActive()) {
            throw new IllegalArgumentException(
                    "Invalid email or password");
        }

        return user;
    }
    
    
}
