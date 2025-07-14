package com.template.users_service.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String passwordHash;

    private String firstName;

    private String lastName;

    private List<UserAddressDto> address;
}