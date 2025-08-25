package com.example.demo.web;

import com.example.demo.service.ItemService;
import com.example.demo.web.dto.CreateItemRequest;
import com.example.demo.web.dto.ItemResponse;
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
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@PostMapping
	public ResponseEntity<ItemResponse> create(
			@Valid @RequestBody CreateItemRequest request,
			@RequestHeader("Authorization") String authHeader) {
		
		// Extract username from JWT token (simplified - in production use proper JWT parsing)
		String token = authHeader.replace("Bearer ", "");
		// For now, we'll need to implement proper JWT parsing to get username
		// This is a placeholder - you'll need to implement JWT token parsing
		String userName = "admin"; // Placeholder
		
		ItemResponse response = itemService.createItem(request, userName);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{itemId}")
	public ResponseEntity<String> deleteItem(
			@PathVariable Integer itemId,
			@RequestHeader("Authorization") String authHeader) {
		
		// Extract username from JWT token (simplified - in production use proper JWT parsing)
		String token = authHeader.replace("Bearer ", "");
		// For now, we'll need to implement proper JWT parsing to get username
		// This is a placeholder - you'll need to implement JWT token parsing
		String userName = "admin"; // Placeholder
		
		itemService.deleteItem(itemId, userName);
		return ResponseEntity.ok("Item deleted successfully");
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
