package com.pet_api.virtual_pet.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtFilter executed for: {}", request.getRequestURI());
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        boolean isUnauthenticated = SecurityContextHolder.getContext().getAuthentication() == null;
        boolean isValidToken = jwtUtil.validateToken(jwt, username);
        if (username != null && isUnauthenticated && isValidToken) {
            String role = jwtUtil.extractRole(jwt);
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            log.info("Authenticated {} with role {} ", username, role);
        }

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        boolean skip = path.startsWith("/api/v1/auth/login")
                || path.startsWith("/api/v1/auth/register")
                || path.startsWith("/h2-console");
        log.info("shouldNotFilter? {} -> {}", path, skip);
        return skip;
    }
}