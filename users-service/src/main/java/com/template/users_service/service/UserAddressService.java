package com.template.users_service.service;

import com.template.users_service.repository.UserAddressRepository;
import com.template.users_service.entity.UserAddress;
import com.template.users_service.repository.UserRepository;
import com.template.users_service.entity.User;
import com.template.users_service.dto.UserAddressDto;
import com.template.users_service.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    private final UserAddressMapper userAddressMapper;

    public UserAddressService(UserAddressRepository userAddressRepository, UserRepository userRepository,
            UserAddressMapper userAddressMapper) {
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
        this.userAddressMapper = userAddressMapper;
    }

    public List<UserAddressDto> getAllAddresses() {
        return userAddressRepository.findAll().stream()
                .map(userAddressMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserAddressDto> getAddressById(UUID id) {
        return userAddressRepository.findById(id).map(userAddressMapper::toDto);
    }

    public UserAddressDto saveAddress(UserAddressDto addressDto) {
        UserAddress address = userAddressMapper.toEntity(addressDto, userRepository);
        return userAddressMapper.toDto(userAddressRepository.save(address));
    }

    public void deleteAddress(UUID id) {
        userAddressRepository.deleteById(id);
    }
}