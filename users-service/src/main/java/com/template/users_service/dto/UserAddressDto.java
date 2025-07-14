package com.template.users_service.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class UserAddressDto {
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
}