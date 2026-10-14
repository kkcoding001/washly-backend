
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
    
    
    // create staff by Admin 
	public User createStaff(User staff) {
	
	    String email = staff.getEmail().trim().toLowerCase();
	
	    if (userRepository.existsByEmail(email)) {
	        throw new IllegalArgumentException(
	                "Email is already registered");
	    }
	
	    staff.setEmail(email);
	    staff.setPassword(
	            passwordEncoder.encode(staff.getPassword()));
	    staff.setRole(Role.STAFF);
	    staff.setActive(true);
	
	    return userRepository.save(staff);
	}
	
	
	
	// get user profile
	public User getProfile(String email) {
	
	    return userRepository.findByEmail(
	            email.trim().toLowerCase())
	            .orElseThrow(() -> new IllegalArgumentException(
	                    "User not found"));
	}
	
	
	// Update user profile
	public User updateProfile(
	        String email, String name, String phone) {
	
	    User user = getProfile(email);
	
	    user.setName(name.trim());
	    user.setPhone(phone);
	
	    return userRepository.save(user);
	}

	

    
    
}
