package com.template.users_service.config;

import com.template.users_service.service.JwtService;
import com.template.users_service.repository.UserRepository;
import com.template.users_service.entity.User;
import com.template.users_service.service.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Configuration
public class SecurityConfig {
    private JwtService jwtService;
    private UserRepository userRepository;
    private TokenBlacklistService tokenBlacklistService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/auth/register", "/api/auth/login").anonymous()
                        .requestMatchers("/api/auth/logout").authenticated())
                .addFilterBefore(new JwtAuthFilter(jwtService, userRepository, tokenBlacklistService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    public static class JwtAuthFilter extends OncePerRequestFilter {
        private final JwtService jwtService;
        private final UserRepository userRepository;
        private final TokenBlacklistService tokenBlacklistService;

        public JwtAuthFilter(JwtService jwtService, UserRepository userRepository,
                TokenBlacklistService tokenBlacklistService) {
            this.jwtService = jwtService;
            this.userRepository = userRepository;
            this.tokenBlacklistService = tokenBlacklistService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                @NonNull HttpServletResponse response,
                @NonNull FilterChain filterChain)
                throws ServletException, IOException {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (tokenBlacklistService.isTokenBlacklisted(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                if (jwtService.isTokenValid(token)) {
                    Claims claims = jwtService.extractAllClaims(token);
                    String email = claims.getSubject();
                    boolean isAdmin = Boolean.TRUE.equals(claims.get("isAdmin", Boolean.class));
                    User user = userRepository.findByEmail(email).orElse(null);
                    if (user != null) {
                        String role = isAdmin ? "ROLE_ADMIN" : "ROLE_USER";
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                email, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}