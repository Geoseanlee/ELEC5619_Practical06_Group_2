package com.example.demo.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WarehouseResponse {
	Integer id;
	String name;
	String location;
	Integer companyId;
	String companyName;
}
