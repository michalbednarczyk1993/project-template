package com.template.users_service.service;

import com.template.users_service.dto.UserDto;
import com.template.users_service.entity.User;
import com.template.users_service.mapper.UserMapper;
import com.template.users_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public boolean setAdminRights(String email, boolean isAdmin) {
        return userRepository.findByEmail(email).map(user -> {
            user.setIsAdmin(isAdmin);
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Transactional
    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
