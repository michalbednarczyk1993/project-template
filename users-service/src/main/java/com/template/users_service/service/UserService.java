package com.template.users_service.service;

import com.template.users_service.repository.UserRepository;
import com.template.users_service.dto.UserDto;
import com.template.users_service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    public Optional<UserDto> updateUserByEmail(String email, UserDto userDto) {
        return userRepository.findByEmail(email).map(user -> {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            // Nie zmieniamy roli ani hasła tutaj
            userRepository.save(user);
            return userMapper.toDto(user);
        });
    }

}