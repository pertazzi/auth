package com.login.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.auth.model.User;
import com.login.auth.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public void save(User user) {
		userRepository.save(user);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public Optional<User> findById(String fiscalCode) {
		return userRepository.findById(fiscalCode);
	}

	// public Optional findByFiscalCode(String fiscalCode) {
	// return userRepository.findById(fiscalCode);
	// }

}