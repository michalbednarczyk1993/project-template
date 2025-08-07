package com.template.users_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}:;\"'\\[\\]|<>,.?/~`]).{10,}$",
            message = "Hasło musi mieć co najmniej 10 znaków, zawierać małą i wielką literę oraz znak specjalny."
    )
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}:;\"'\\[\\]|<>,.?/~`]).{10,}$",
            message = "Hasło musi mieć co najmniej 10 znaków, zawierać małą i wielką literę oraz znak specjalny."
    )
    private String confirmPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Valid
    @NotEmpty(message = "Adresy użytkownika nie mogą być puste")
    private List<UserAddressDto> address;
}