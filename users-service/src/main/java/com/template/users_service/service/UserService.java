package com.template.users_service.service;

import com.template.users_service.repository.UserRepository;
import com.template.users_service.entity.User;
import com.template.users_service.dto.UserDto;
import com.template.users_service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}