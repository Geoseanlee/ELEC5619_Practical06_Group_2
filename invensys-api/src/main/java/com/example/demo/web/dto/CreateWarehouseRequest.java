package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateWarehouseRequest {

	@NotBlank
	private String name;

	@NotBlank
	private String location;
}
