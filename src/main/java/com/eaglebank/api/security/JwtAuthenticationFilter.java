package com.eaglebank.api.security;

import com.eaglebank.api.model.dto.response.ErrorResponse;
import com.eaglebank.api.validation.exception.CommonException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Filter that intercepts HTTP requests to validate JWT tokens.
 * If a valid token is present, sets authentication context for the request.
 * Handles JWT errors by returning a JSON error response with appropriate status code.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Intercepts each HTTP request, extracts and validates JWT token.
     * Sets authentication if token is valid, otherwise handles errors.
     *
     * @param request      The incoming HTTP request.
     * @param response     The outgoing HTTP response.
     * @param filterChain  The filter chain to continue processing.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = extractToken(request);
            if (StringUtils.isNotBlank(token)) {
                String username = jwtUtil.extractUsername(token);

                // Set authentication context for the request
                AuthUtils.createAuthentication(username);
            }

            // Continue with the filter chain
            filterChain.doFilter(request, response);

        } catch (CommonException ex) {
            // Handle JWT-related errors and send JSON error response
            handleJwtError(response, ex, request.getRequestURI());
        }
    }

    /**
     * Extracts the JWT token from the Authorization header of the request.
     *
     * @param request The HTTP request.
     * @return The JWT token string, or null if not present.
     */
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * Handles JWT errors by writing a JSON error response with the appropriate status code.
     *
     * @param response The HTTP response.
     * @param ex       The exception that occurred.
     * @param path     The request URI path.
     * @throws IOException If writing to the response fails.
     */
    private void handleJwtError(HttpServletResponse response,
                                CommonException ex,
                                String path) throws IOException {

        ErrorResponse error = new ErrorResponse().setMessage(ex.getMessage());

        response.setStatus(ex.getStatusCode());
        response.setContentType("application/json");

        String json = new ObjectMapper().writeValueAsString(error);
        response.getWriter().write(json);
    }
}
