package com.template.users_service.controller;

import com.template.users_service.dto.LoginRequest;
import com.template.users_service.dto.LoginResponse;
import com.template.users_service.dto.RegisterRequest;
import com.template.users_service.dto.UserDto;
import com.template.users_service.service.JwtService;
import com.template.users_service.service.LoginService;
import com.template.users_service.service.RegistrationService;
import com.template.users_service.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenBlacklistService tokenBlacklistService;
    private final LoginService loginService;
    private final RegistrationService registrationService;
    private final JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserDto user = registrationService.registerUser(request);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = loginService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Wyciągamy datę wygaśnięcia tokena
            try {
                long exp = ((Number) jwtService.extractAllClaims(token).getExpiration().getTime())
                        .longValue();
                tokenBlacklistService.blacklistToken(token, exp);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok().build();
    }
}

