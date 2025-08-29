package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	// 256-bit base64 secret for HS256 (example only; move to config for production)
	private static final String SECRET_B64 = "ZmFrZV9zZWNyZXRfZm9yX2RlbW9fcHJvamVjdF9lbGVjNTYxOQ==";

	private Key signingKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_B64);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String subject, Map<String, Object> claims, long ttlMillis) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + ttlMillis);
		return Jwts.builder()
				.setSubject(subject)
				.addClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(signingKey(), SignatureAlgorithm.HS256)
				.compact();
	}
}


