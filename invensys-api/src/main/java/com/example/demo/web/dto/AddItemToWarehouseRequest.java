package com.example.demo.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddItemToWarehouseRequest {

	@NotNull
	private Integer itemId;

	@NotNull
	@Positive
	private Integer amount;

	@NotNull
	private Integer warehouseId;
}
