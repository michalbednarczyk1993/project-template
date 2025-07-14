package com.template.users_service.service;

import com.template.users_service.dto.RegisterRequest;
import com.template.users_service.dto.UserDto;
import com.template.users_service.entity.User;
import com.template.users_service.mapper.UserMapper;
import com.template.users_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Value("${user.password.pepper}")
    private final String passwordPepper;

    @Transactional
    public UserDto registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Użytkownik o podanym e-mailu już istnieje.");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Hasła nie są identyczne.");
        }
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword() + passwordPepper));
        user.setIsAdmin(false);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);


        return userMapper.toDto(user);
    }
}
