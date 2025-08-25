package com.example.demo.web.dto;

import com.example.demo.model.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAccountRequest {

	@NotBlank
	private String userName;

	@NotBlank
	private String password;

	@NotNull
	private AccountType type;

	// If companyId present -> join; else create with companyName
	private Integer companyId;

	private String companyName;
}


