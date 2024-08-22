//This service loads user data from the database by username, 
//which is essential for the authentication process in Spring Security
package com.ecommerce.userservice.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user= userRepository.findByUsername(username);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username:");
		}
		return new org.springframework.security.core.userdetails.User(
				user.get().getUsername(),
				user.get().getPassword(),
				user.get().getRoles().stream()
				.map(role-> new org.springframework.security.core.authority
						.SimpleGrantedAuthority(role.name())).toList());
	}

}
