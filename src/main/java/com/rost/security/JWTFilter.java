package com.rost.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private static final String authorization = "Authorization";
    private static final String jwtTokenPrefix = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(authorization);

        if (isJwtAuthHeaderPresent(authHeader)) {
            String jwt = StringUtils.substringAfter(authHeader, jwtTokenPrefix);
            if (jwt.isBlank())
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");

            try {
                String username = jwtUtil.validateTokeAndRetrieveClaim(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication token = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                if (SecurityContextHolder.getContext().getAuthentication() == null)
                    SecurityContextHolder.getContext().setAuthentication(token);
            } catch (JWTVerificationException | UsernameNotFoundException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token");
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isJwtAuthHeaderPresent(String authHeader) {
        return authHeader != null && !authHeader.isBlank() && authHeader.startsWith(jwtTokenPrefix);
    }
}
