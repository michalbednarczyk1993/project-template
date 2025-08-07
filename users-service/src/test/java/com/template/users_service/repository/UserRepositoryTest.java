package com.template.users_service.repository;

import com.template.users_service.TestContainerInitializer;
import com.template.users_service.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = TestContainerInitializer.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUserToDatabase() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .passwordHash("hashed-password")
                .isAdmin(false)
                .firstName("Jan")
                .lastName("Kowalski")
                .build();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(userRepository.findById(savedUser.getId())).isPresent();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }
}