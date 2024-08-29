package com.ecommerce.productservice.filter;

import com.ecommerce.productservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response, 
//                                    FilterChain filterChain)
//            throws ServletException, IOException {}

	@Override
	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            username = jwtUtil.extractUsername(jwt);
//        }
        
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            System.out.println("Authorization Header: " + authorizationHeader);  // Log the entire header
            System.out.println("Extracted JWT Token: " + jwt);  // Log the extracted token
        } else {
            System.out.println("Authorization header is missing or does not start with Bearer");  // Log if the header is incorrect
        }
        
        

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Logging the JWT and username
            System.out.println("JWT Token: " + jwt);
            System.out.println("Extracted Username: " + username);

            if (jwtUtil.validateToken(jwt)) {
                UserDetails userDetails = jwtUtil.getUserDetailsFromToken(jwt);

                // Logging user details and authorities
                System.out.println("User Details Retrieved: " + userDetails.getUsername());
                System.out.println("User Roles: " + userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                // Logging the security context
                System.out.println("SecurityContext Authentication: " + SecurityContextHolder.getContext().getAuthentication());
            } else {
                // Logging token validation failure
                System.out.println("Token validation failed for token: " + jwt);
            }
        } else {
            // Logging case where username is null or already authenticated
            System.out.println("Username is null or user is already authenticated.");
        }

        filterChain.doFilter(request, response);
    }
}
