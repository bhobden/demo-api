package com.eaglebank.api.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUsername() {
        Authentication auth = getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

    public static boolean isAuthenticated() {
        Authentication auth = getAuthentication();
        return (auth != null && auth.isAuthenticated());
    }

    public static boolean isAnonymous() {
        Authentication auth = getAuthentication();
        return (auth == null || !auth.isAuthenticated());
    }

    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    public static void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static void createAuthentication(String username) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }   
}

