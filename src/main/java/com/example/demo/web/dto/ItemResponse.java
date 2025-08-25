package com.example.demo.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemResponse {
	Integer id;
	String name;
	Integer weight;
}
