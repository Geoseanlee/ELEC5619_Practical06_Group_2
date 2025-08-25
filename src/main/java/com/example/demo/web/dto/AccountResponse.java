package com.example.demo.web.dto;

import com.example.demo.model.AccountType;
import com.example.demo.model.Level;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountResponse {
	Integer id;
	String userName;
	AccountType type;
	Level level;
	Boolean valid;
	Integer companyId;
	String companyName;
}


