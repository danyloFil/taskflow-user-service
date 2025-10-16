package com.taskflow.user.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

    // Secret key used to sign and verify JWT tokens
    private final String secretKey = "your-super-secure-and-very-long-secret-key";

    // Generates a JWT token with the username and 1-hour validity
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Sets the username as the subject of the token
                .setIssuedAt(new Date()) // Sets the token issuance date and time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Sets the token expiration date and time (1 hour from issuance)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256) // Converts the secret key to bytes and signs the token
                .compact(); // Builds the JWT token
    }

    // Extracts the claims from the JWT token (additional token information)
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))) // Sets the secret key to validate the token signature
                .build()
                .parseClaimsJws(token) // Parses the JWT token and obtains the claims
                .getBody(); // Returns the body of the claims
    }

    // Extracts the username from the JWT token
    public String extractUsername(String token) {
        // Gets the subject (username) from the token claims
        return extractClaims(token).getSubject();
    }

    // Checks if the JWT token is expired
    public boolean isTokenExpired(String token) {
        // Compares the token expiration date with the current date
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Validates the JWT token by checking the username and that the token is not expired
    public boolean validateToken(String token, String username) {
        // Extracts the username from the token
        final String extractedUsername = extractUsername(token);
        // Checks if the username from the token matches the provided one and that the token is not expired
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
