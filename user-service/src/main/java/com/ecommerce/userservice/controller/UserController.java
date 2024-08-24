package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.ecommerce.userservice.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user-service")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/admin/getusers")
	public List<User> getAllUsers() {
		System.out.println("get all users api is called");
		return userRepository.findAll();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/admin/getuser/{id}")
	public User getUserById(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/admin/updateuser/{id}/role")
	public User updateUserRole(@PathVariable Long id, @RequestBody Set<Role> updatedRoles) {
		return userRepository.findById(id).map(user -> {
			user.setRoles(updatedRoles);
			return userRepository.save(user);
		}).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/admin/deleteuser/{id}")
	public String deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
		return "User deleted successfully";
	}

	// User APIs
	@PutMapping("/user/update")
	public ResponseEntity<String> updateUserDetails(@RequestBody Map<String, String> updates) {
		String newUsername = updates.get("username");
		String newPassword = updates.get("password");

		User currentUser = getCurrentUser(); // Method to get the currently logged-in user

		if (newUsername != null && !newUsername.isEmpty()) {
			// Check if the new username is already taken
			if (userRepository.findByUsername(newUsername).isPresent()) {
				return ResponseEntity.badRequest().body("Username is already taken");
			}
			currentUser.setUsername(newUsername);
		}

		if (newPassword != null && !newPassword.isEmpty()) {
			currentUser.setPassword(passwordEncoder.encode(newPassword));
		}

		userRepository.save(currentUser);
		return ResponseEntity.ok("User details updated successfully");
	}
	@GetMapping("/user/me")
	public User getCurrentUserDetails() {
	    return getCurrentUser();
	}


	public User getCurrentUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    String currentUsername;
	    if (authentication.getPrincipal() instanceof UserDetails) {
	        currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
	    } else {
	        currentUsername = authentication.getPrincipal().toString();
	    }

	    return userRepository.findByUsername(currentUsername)
	            .orElseThrow(() -> new RuntimeException("User not found with username: " + currentUsername));
	}


}
