package com.template.users_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = TestContainerInitializer.class)
class UsersServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
