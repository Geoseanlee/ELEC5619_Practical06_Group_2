package com.example.demo.web;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
		String userName = body.get("userName");
		String password = body.get("password");
		User user = userRepository.findByUserName(userName).orElse(null);
		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
		
		// Check if user account is valid
		if (!user.getValid()) {
			return ResponseEntity.status(403).body("Account pending approval. Please contact administrator to activate your account.");
		}
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("uid", user.getId());
		claims.put("type", user.getType() != null ? user.getType().name() : null);
		claims.put("level", user.getLevel() != null ? user.getLevel().name() : null);
		String token = jwtService.generateToken(user.getUserName(), claims, 3600_000);
		Map<String, Object> resp = new HashMap<>();
		resp.put("token", token);
		return ResponseEntity.ok(resp);
	}
}
