package com.template.users_service.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
public class UserAddressDto {
    private UUID id;
    private UUID userId;

    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
}