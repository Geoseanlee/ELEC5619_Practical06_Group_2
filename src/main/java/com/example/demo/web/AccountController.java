package com.example.demo.web;

import com.example.demo.service.AccountService;
import com.example.demo.web.dto.AccountResponse;
import com.example.demo.web.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@PostMapping
	public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
		AccountResponse resp = accountService.createAccount(request);
		return ResponseEntity.ok(resp);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}


