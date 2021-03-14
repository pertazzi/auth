package com.login.auth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.login.auth.model.User;
import com.login.auth.repository.UserRepository;
import com.login.auth.service.UserService;

import lombok.extern.slf4j.Slf4j;
import utils.GenerateJwt;

@Slf4j
@RestController
@RequestMapping(path = "", produces = "application/json")
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

//	@Bean
//	public PasswordEncoder encoder() {
//		SecureRandom random = new SecureRandom();
//		byte bytes[] = new byte[0];
//		random.nextBytes(bytes);
//		return new BCryptPasswordEncoder(10, random);
//		return new BCryptPasswordEncoder();
//	}

	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody User newUser) {
		if (!newUser.getFiscalCode().equals("") && !newUser.getName().equals("") && !newUser.getSurname().equals("")
				&& !newUser.getUsername().equals("") && !newUser.getPassword().equals("")) {
			// Controllo presenza dell'utente prima del salvataggio
			if (userRepository.findById(newUser.getFiscalCode()).isPresent()) {
				log.error("A user already exists: {}", newUser);
				return new ResponseEntity<>("A user already exists: cannot register", HttpStatus.CONFLICT);
			} else {
//				newUser.setPassword(encoder().encode(newUser.getPassword()));
				newUser.setPassword(DigestUtils.sha256Hex(newUser.getPassword()));
				User user = userRepository.save(newUser);
				if (user != null) {
//					log.info("New user registered: {}", user.getName());
					log.info("New user registered: ", newUser.getUsername());
					String jwt = GenerateJwt.generateToken(user);
					Map<String, Object> header = new HashMap<String, Object>();
					header.put("registration token", jwt);
					return new ResponseEntity<>(header, HttpStatus.OK);
				}
			}
		} else {
			return new ResponseEntity<>("Error: missing fields ", HttpStatus.BAD_REQUEST);
		}
		log.error("Cannot register user: ", newUser.getUsername());
		return new ResponseEntity<>("Cannot register user", HttpStatus.CONFLICT);
	}

	@GetMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password) {
		try {
			Optional<User> userFound = userRepository.findByUsername(username);
			if (userFound.isPresent()) {
//				String pwdFromRest = encoder().encode(password);
				String pwdFromRest = DigestUtils.sha256Hex(password);
				if (pwdFromRest.equals(userFound.get().getPassword())) {
					User user = userFound.get();

					String jwt = GenerateJwt.generateToken(user);
					Map<String, Object> header = new HashMap<String, Object>();
					header.put("token", jwt);
					return new ResponseEntity<>(header, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("User doesn't exists or wrong password", HttpStatus.UNAUTHORIZED);
				}
			} else {
				return new ResponseEntity<>("User " + username + " not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Username not found");
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
	}
}
