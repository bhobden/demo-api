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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = extractToken(request);
            if (StringUtils.isNotBlank(token)) {
                String username = jwtUtil.extractUsername(token);

                AuthUtils.createAuthentication(username);
            }

            filterChain.doFilter(request, response);

        } catch (CommonException ex) {
            handleJwtError(response, ex, request.getRequestURI());
        }
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

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
