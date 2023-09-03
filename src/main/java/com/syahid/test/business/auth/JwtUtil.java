package com.syahid.test.business.auth;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	String jwtSecret;

	@Value("${jwt.expirationms}")
	int jwtExpirationMs;

	public String generate(String username) {
    	return Jwts.builder()
    			.setSubject(username)
    			.setIssuedAt(new Date())
    			.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
    			.signWith(key(), SignatureAlgorithm.HS256)
    			.compact();
	}
  
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String getUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key()).build()
				.parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validate(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException | 
    		ExpiredJwtException | 
    		UnsupportedJwtException |
    		IllegalArgumentException e) {
			return false;
		}
	}
}
