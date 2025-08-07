package com.template.users_service.service;

import com.template.users_service.dto.RegisterRequest;
import com.template.users_service.dto.UserDto;
import com.template.users_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class RegistrationServiceTest {

    private final RegistrationService registrationService;

    @InjectMocks
    private final UserRepository userRepository;

    @Test
    public void registrationPasswordsDoesNotMatch() {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("sample@gmail.com")
                .password("StrongPassword123")
                .confirmPassword("StrongPassword124")
                .build();

        // when && then
        assertThrows(IllegalArgumentException.class, () -> registrationService.registerUser(registerRequest));
    }

    @Test
    public void registrationEmailAlreadyExists() {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("sample@gmail.com")
                .password("StrongPassword123")
                .confirmPassword("StrongPassword123")
                .build();
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        // when && then
        assertThrows(IllegalArgumentException.class, () -> registrationService.registerUser(registerRequest));
    }

    @Test
    public void registrationHappyPath() {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@example.com")
                .password("StrongPassword123")
                .confirmPassword("StrongPassword123")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);

        // when
        UserDto userDto = registrationService.registerUser(registerRequest);

        // then
        assertEquals(registerRequest.getEmail(), userDto.getEmail());
        assertEquals(registerRequest.getFirstName(), userDto.getFirstName());
        assertEquals(registerRequest.getLastName(), userDto.getLastName());
    }

}