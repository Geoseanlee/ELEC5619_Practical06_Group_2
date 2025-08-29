package com.example.demo.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemRecordResponse {
	Integer id;
	Integer itemId;
	String itemName;
	Integer amount;
	Integer warehouseId;
	String warehouseName;
	Integer companyId;
	String companyName;
}
