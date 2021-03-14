package utils;

import java.util.Date;

import com.login.auth.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateJwt {

//	@Autowired
//	static JwtConfig jwtSecret;

	/*
	 * Metodo per la generazione del Jwt, torno solamente gli elementi fondamentali
	 * inserendoli successivamente nell'header della ResponseEntity lato Controller
	 */
	public static String generateToken(User u) {
		Claims claims = Jwts.claims().setSubject(u.getUsername());
		claims.put("userId", u.getFiscalCode()); // id
		claims.put("username", u.getUsername());
		Date expiration = new Date(System.currentTimeMillis() + 7200 * 1000);

		return Jwts.builder().setClaims(claims).setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, "UKfjEW0ohJ^1gzk$EQb*Xm%5RP7O").compact();
	}
}
