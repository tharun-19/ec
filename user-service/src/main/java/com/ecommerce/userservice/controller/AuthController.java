package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.service.CustomUserDetailsService;
import com.ecommerce.userservice.service.JwtUtil;
import com.ecommerce.userservice.dto.AuthenticationRequest;
import com.ecommerce.userservice.dto.AuthenticationResponse;
import com.ecommerce.userservice.repository.UserRepository;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new RuntimeException("Username is already taken");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Set.of(Role.USER));
		userRepository.save(user);
		
		return "User registered successfully";
	}

	@PostMapping("/authenticate")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
//			System.out.println(authenticationRequest);
			System.out.println(authenticationRequest.getUsername());
			System.out.println(authenticationRequest.getPassword());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return new AuthenticationResponse(jwt);
	}
}
