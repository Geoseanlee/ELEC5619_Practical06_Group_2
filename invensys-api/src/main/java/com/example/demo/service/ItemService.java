package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.dto.CreateItemRequest;
import com.example.demo.web.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;
	private final UserRepository userRepository;

	@Transactional
	public ItemResponse createItem(CreateItemRequest req, String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if (!user.getValid()) {
			throw new IllegalArgumentException("User account is not active");
		}
		
		if (user.getType() != com.example.demo.model.AccountType.INVENTORY_MANAGER) {
			throw new IllegalArgumentException("Only INVENTORY_MANAGER users can create items");
		}
		
		// Check if item name already exists
		itemRepository.findByName(req.getName()).ifPresent(i -> {
			throw new IllegalArgumentException("Item name already exists");
		});
		
		Item item = Item.builder()
				.name(req.getName())
				.weight(req.getWeight())
				.build();
		
		item = itemRepository.save(item);
		
		return ItemResponse.builder()
				.id(item.getId())
				.name(item.getName())
				.weight(item.getWeight())
				.build();
	}

	@Transactional
	public void deleteItem(Integer itemId, String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if (!user.getValid()) {
			throw new IllegalArgumentException("User account is not active");
		}
		
		if (user.getType() != com.example.demo.model.AccountType.INVENTORY_MANAGER) {
			throw new IllegalArgumentException("Only INVENTORY_MANAGER users can delete items");
		}
		
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new IllegalArgumentException("Item not found"));
		
		// Check if item is used in any warehouse
		// Note: This would require a repository method to check if item is referenced
		// For now, we'll allow deletion but in production you might want to add this check
		
		itemRepository.delete(item);
	}
}
