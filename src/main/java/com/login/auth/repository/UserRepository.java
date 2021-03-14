package com.login.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.login.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	public User findByName(String name);

	// public boolean findByFiscalCode(String fiscalCode);

//	@Query("SELECT u FROM User u WHERE u.username = :username")
//	public boolean existsByUsername(String username);
	
	/*
	 * Recupera l'utente da db e verifica la pwd, verifica quindi le credenziali
	 */
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public Optional<User> findByUsername(@Param("username") String username);
}
