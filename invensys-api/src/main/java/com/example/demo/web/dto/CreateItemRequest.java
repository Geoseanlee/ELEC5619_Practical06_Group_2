package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateItemRequest {

	@NotBlank
	private String name;

	@NotNull
	@Positive
	private Integer weight; // in grams
}
