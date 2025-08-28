package com.example.Healthcare_System_API.service;

import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.repository.postgres.UsersRepository;
import com.example.Healthcare_System_API.security.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    private UsersRepository userRepository;

    // Use a fixed secret from application.properties
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate JWT with role + permissions
    public String generateToken(String username) {
        Users u = userRepository.findByUsername(username);
        if (u == null) throw new RuntimeException("User not found: " + username);

        Map<String, Object> claims = Map.of(
                "role", u.getRole().name(),
                "permissions", u.getRole().getPermissions().stream()
                        .map(Permission::getValue)
                        .collect(Collectors.toList())
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 min
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract permissions safely
    public List<String> extractPermissions(String token) {
        Claims claims = extractAllClaims(token);
        List<?> rawPermissions = claims.get("permissions", List.class);

        if (rawPermissions == null) return List.of();
        return rawPermissions.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    // Check if token expired
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    //validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        String tokenUsername = extractUserName(token);
        return (tokenUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Helper to extract any claim
    private <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    // Helper to parse all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
