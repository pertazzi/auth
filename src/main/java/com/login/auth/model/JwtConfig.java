package com.login.auth.model;

//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//import lombok.Data;

//@Data
//@Configuration
//@ConfigurationProperties(prefix = "jwt.token") // prendo il valore dal yaml
public class JwtConfig {

	private String secret;

	private int expiration;
}
