package com.template.users_service.service;

import com.template.users_service.dto.LoginRequest;
import com.template.users_service.dto.LoginResponse;
import com.template.users_service.entity.User;
import com.template.users_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${user.password.pepper}")
    private final String passwordPepper;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy e-mail lub hasło."));
        boolean passwordMatches = passwordEncoder.matches(request.getPassword() + passwordPepper,
                user.getPasswordHash());
        if (!passwordMatches) {
            throw new IllegalArgumentException("Nieprawidłowy e-mail lub hasło.");
        }
        String token = jwtService.generateToken(user.getEmail(), Boolean.TRUE.equals(user.getIsAdmin()));
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setIsAdmin(Boolean.TRUE.equals(user.getIsAdmin()));
        return response;
    }

    public void logout(String servletRequestHeader) {
        if (servletRequestHeader != null && servletRequestHeader.startsWith("Bearer ")) {
            String token = servletRequestHeader.substring(7);
            // Wyciągamy datę wygaśnięcia tokena

            long exp = ((Number) jwtService.extractAllClaims(token)
                    .getExpiration()
                    .getTime())
                    .longValue();
            tokenBlacklistService.blacklistToken(token, exp);
        }
    }
}
