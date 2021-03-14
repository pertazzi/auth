package com.login.auth.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.login.auth.model.User;
import com.login.auth.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
	void save() {
		User user = new User("PRTMRC87M11A851V", "pertica", passwordEncoder.encode("pwd"), "Marco", "Perticucci");
		userService.save(user);
		Optional<User> userFound = userRepository.findById("PRTMRC87M11A851V");
		Assertions.assertTrue(userFound.isPresent());
	}

//	@Test
//	void findById() {
//		Assertions.assertEquals(true, userService.findById("PRTMRC87M11A851V").isPresent());
//	}

//	@Test
//	void loginUser() {
//		boolean userFound = userRepository.existsByUsername("pertica");
//		Assertions.assertNotNull(userFound);
//	}

	@Test
	void findByUsername() {
		Optional<User> userFound = userRepository.findByUsername("pertica");
		Assertions.assertNotNull(userFound);
	}

}
