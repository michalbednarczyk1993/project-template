package com.template.users_service.mapper;

import com.template.users_service.entity.UserAddress;
import com.template.users_service.entity.User;
import com.template.users_service.repository.UserRepository;
import com.template.users_service.dto.UserAddressDto;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserAddressMapper {
    public UserAddressDto toDto(UserAddress address) {
        UserAddressDto dto = new UserAddressDto();
        dto.setId(address.getId());
        dto.setUserId(address.getUser() != null ? address.getUser().getId() : null);
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setZipCode(address.getZipCode());
        return dto;
    }

    public UserAddress toEntity(UserAddressDto dto, UserRepository userRepository) {
        UserAddress address = new UserAddress();
        address.setId(dto.getId());
        if (dto.getUserId() != null) {
            Optional<User> userOpt = userRepository.findById(dto.getUserId());
            userOpt.ifPresent(address::setUser);
        }
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setZipCode(dto.getZipCode());
        return address;
    }
}