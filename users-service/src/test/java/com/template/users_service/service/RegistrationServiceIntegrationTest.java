package com.template.users_service.service;

import com.template.users_service.dto.RegisterRequest;
import com.template.users_service.entity.User;
import com.template.users_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // do not replace the testcontainer data source
@Testcontainers
public class RegistrationServiceIntegrationTest {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testPasswordRegisteredInDatabse() {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("sample@gmail.com")
                .password("StrongPassword123")
                .confirmPassword("StrongPassword123")
                .build();

        // when
        registrationService.registerUser(registerRequest);

        // then
        Optional<User> user = userRepository.findByEmail(registerRequest.getEmail());
        assertTrue(user.isPresent(), "User should be present in the database");
        assertNotEquals(user.get().getPasswordHash(), registerRequest.getPassword(), "Password hash should not match the plain password");
        assertEquals(new BCryptPasswordEncoder().encode(registerRequest.getPassword()), user.get().getPasswordHash(), "Encoded password should match the stored hash");
    }

}
