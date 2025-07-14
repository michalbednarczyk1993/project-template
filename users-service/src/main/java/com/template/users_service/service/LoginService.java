package com.template.users_service.service;

import com.template.users_service.dto.LoginRequest;
import com.template.users_service.dto.LoginResponse;
import com.template.users_service.dto.RegisterRequest;
import com.template.users_service.dto.UserDto;
import com.template.users_service.entity.User;
import com.template.users_service.mapper.UserMapper;
import com.template.users_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Value("${user.password.pepper}")
    private final String passwordPepper;
    private final JwtService jwtService;


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
}
