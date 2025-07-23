package com.eaglebank.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import com.eaglebank.api.validation.exception.CommonException;
import com.eaglebank.api.validation.exception.CommonExceptionType;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens using RSA keys.
 * <p>
 * Handles secure creation, parsing, and validation of JWTs for authentication and authorization.
 * Loads private and public keys from the classpath for signing and verifying tokens.
 * </p>
 */
@Component
public class JwtUtil {

    // A secret key for signing tokens — store securely in production!
    private final PrivateKey PRIVATE_SECRET;
    private final PublicKey PUBLIC_SECRET;

    /**
     * Loads the RSA private and public keys from the classpath.
     * Throws a RuntimeException if the keys cannot be loaded.
     */
    public JwtUtil() {
        try {
            String privateKeyPath = getClass().getClassLoader()
                    .getResource("keys/private_pkcs8.pem").toURI().getPath();
            this.PRIVATE_SECRET = KeyLoader.loadPrivateKey(privateKeyPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }

        try {
            String publicKeyPath = getClass().getClassLoader()
                    .getResource("keys/public.pem").toURI().getPath();
            this.PUBLIC_SECRET = KeyLoader.loadPublicKey(publicKeyPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    /**
     * Generates a JWT for the given username.
     *
     * @param username the user to include in the token payload
     * @return signed JWT string
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("eaglebank-auth")
                .setAudience("eaglebank-api")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now()
                        .plusMinutes(50) // Token validity period
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(PRIVATE_SECRET, SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * Extracts and validates claims from a JWT token.
     *
     * @param token the JWT string
     * @return Claims object containing the token's payload
     * @throws CommonException if the token is invalid
     */
    public Claims extractClaims(String token) {
        try {
        return Jwts.parserBuilder()
                .setSigningKey(PUBLIC_SECRET)
                .requireIssuer("eaglebank-auth")
                .requireAudience("eaglebank-api")
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
            throw new CommonException(CommonExceptionType.INVALID_JWT); 
        }
    }

    /**
     * Extracts the username (subject) from a JWT token after validating it.
     *
     * @param token the JWT string
     * @return the username (subject) from the token
     * @throws CommonException if the token is invalid or expired
     */
    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        validateToken(claims);
        return claims.getSubject();
    }

    /**
     * Validates the expiration of the JWT claims.
     *
     * @param claims the Claims object to validate
     * @throws CommonException if the token is expired
     */
    public void validateToken(Claims claims) {
        if (claims.getExpiration().before(new Date())) {
            throw new CommonException(CommonExceptionType.EXPIRED_JWT); 
        }
    }
}
