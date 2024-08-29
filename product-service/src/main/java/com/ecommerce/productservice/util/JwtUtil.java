package com.ecommerce.productservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

//    private String SECRET_KEY = "your_secret_key"; // Ensure this is secure and consistent across services
    
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
//    	System.out.println("1");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
//    	System.out.println("2");
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    	System.out.println("3");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
//    	System.out.println("4");
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
//    	System.out.println("5");
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
//    	System.out.println("6");
        return !isTokenExpired(token);
    }


    public UserDetails getUserDetailsFromToken(String token) {
        String username = extractUsername(token);
        Claims claims = extractAllClaims(token);

        // Extract roles from the claims
        var roles = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        System.out.println("username is "+ username);
        System.out.println("claims is "+claims);
        System.out.println("roles is "+claims.get("roles"));
        return new User(username, "", roles);  // Password is not needed here
    }

}
