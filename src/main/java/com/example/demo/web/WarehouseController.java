package com.example.demo.web;

import com.example.demo.service.WarehouseService;
import com.example.demo.web.dto.CreateWarehouseRequest;
import com.example.demo.web.dto.WarehouseResponse;
import com.example.demo.web.dto.AddItemToWarehouseRequest;
import com.example.demo.web.dto.ItemRecordResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

	private final WarehouseService warehouseService;

	@PostMapping
	public ResponseEntity<WarehouseResponse> create(
			@Valid @RequestBody CreateWarehouseRequest request,
			@RequestHeader("Authorization") String authHeader) {
		
		// Extract username from JWT token (simplified - in production use proper JWT parsing)
		String token = authHeader.replace("Bearer ", "");
		// For now, we'll need to implement proper JWT parsing to get username
		// This is a placeholder - you'll need to implement JWT token parsing
		String userName = "admin"; // Placeholder
		
		WarehouseResponse response = warehouseService.createWarehouse(request, userName);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/items")
	public ResponseEntity<ItemRecordResponse> addItem(
			@Valid @RequestBody AddItemToWarehouseRequest request,
			@RequestHeader("Authorization") String authHeader) {
		
		// Extract username from JWT token (simplified - in production use proper JWT parsing)
		String token = authHeader.replace("Bearer ", "");
		// For now, we'll need to implement proper JWT token parsing to get username
		// This is a placeholder - you'll need to implement JWT token parsing
		String userName = "admin"; // Placeholder
		
		ItemRecordResponse response = warehouseService.addItemToWarehouse(request, userName);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{warehouseId}")
	public ResponseEntity<String> deleteWarehouse(
			@PathVariable Integer warehouseId,
			@RequestHeader("Authorization") String authHeader) {
		
		// Extract username from JWT token (simplified - in production use proper JWT parsing)
		String token = authHeader.replace("Bearer ", "");
		// For now, we'll need to implement proper JWT parsing to get username
		// This is a placeholder - you'll need to implement JWT token parsing
		String userName = "admin"; // Placeholder
		
		warehouseService.deleteWarehouse(warehouseId, userName);
		return ResponseEntity.ok("Warehouse deleted successfully");
	}

	@DeleteMapping("/{warehouseId}/items/{itemId}")
	public ResponseEntity<String> deleteItemFromWarehouse(
			@PathVariable Integer warehouseId,
			@PathVariable Integer itemId,
			@RequestHeader("Authorization") String authHeader) {
		
		// Extract username from JWT token (simplified - in production use proper JWT parsing)
		String token = authHeader.replace("Bearer ", "");
		// For now, we'll need to implement proper JWT parsing to get username
		// This is a placeholder - you'll need to implement JWT token parsing
		String userName = "admin"; // Placeholder
		
		warehouseService.deleteItemFromWarehouse(itemId, warehouseId, userName);
		return ResponseEntity.ok("Item removed from warehouse successfully");
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
