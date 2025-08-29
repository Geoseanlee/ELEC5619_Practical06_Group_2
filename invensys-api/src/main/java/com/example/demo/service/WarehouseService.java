package com.example.demo.service;

import com.example.demo.model.Company;
import com.example.demo.model.Item;
import com.example.demo.model.ItemRecord;
import com.example.demo.model.User;
import com.example.demo.model.Warehouse;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ItemRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.web.dto.CreateWarehouseRequest;
import com.example.demo.web.dto.WarehouseResponse;
import com.example.demo.web.dto.AddItemToWarehouseRequest;
import com.example.demo.web.dto.ItemRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

	private final WarehouseRepository warehouseRepository;
	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final ItemRepository itemRepository;
	private final ItemRecordRepository itemRecordRepository;

	@Transactional
	public WarehouseResponse createWarehouse(CreateWarehouseRequest req, String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if (!user.getValid()) {
			throw new IllegalArgumentException("User account is not active");
		}
		
		if (user.getType() != com.example.demo.model.AccountType.INVENTORY_MANAGER) {
			throw new IllegalArgumentException("Only INVENTORY_MANAGER users can create warehouses");
		}
		
		Company company = user.getCompany();
		if (company == null) {
			throw new IllegalArgumentException("User must belong to a company");
		}
		
		// Check if warehouse name already exists
		warehouseRepository.findByName(req.getName()).ifPresent(w -> {
			throw new IllegalArgumentException("Warehouse name already exists");
		});
		
		Warehouse warehouse = Warehouse.builder()
				.name(req.getName())
				.location(req.getLocation())
				.company(company)
				.build();
		
		warehouse = warehouseRepository.save(warehouse);
		
		return WarehouseResponse.builder()
				.id(warehouse.getId())
				.name(warehouse.getName())
				.location(warehouse.getLocation())
				.companyId(company.getId())
				.companyName(company.getName())
				.build();
	}

	@Transactional
	public ItemRecordResponse addItemToWarehouse(AddItemToWarehouseRequest req, String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if (!user.getValid()) {
			throw new IllegalArgumentException("User account is not active");
		}
		
		if (user.getType() != com.example.demo.model.AccountType.INVENTORY_MANAGER) {
			throw new IllegalArgumentException("Only INVENTORY_MANAGER users can add items to warehouses");
		}
		
		// Find the item
		Item item = itemRepository.findById(req.getItemId())
				.orElseThrow(() -> new IllegalArgumentException("Item not found"));
		
		// Find the warehouse
		Warehouse warehouse = warehouseRepository.findById(req.getWarehouseId())
				.orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
		
		// Check if user belongs to the same company as the warehouse
		if (!user.getCompany().getId().equals(warehouse.getCompany().getId())) {
			throw new IllegalArgumentException("You can only add items to warehouses in your company");
		}
		
		// Check if item record already exists for this item in this warehouse
		ItemRecord existingRecord = itemRecordRepository.findByItemAndWarehouse(item, warehouse)
				.orElse(null);
		
		if (existingRecord != null) {
			// Update existing record
			existingRecord.setAmount(existingRecord.getAmount() + req.getAmount());
			existingRecord = itemRecordRepository.save(existingRecord);
			
			return ItemRecordResponse.builder()
					.id(existingRecord.getId())
					.itemId(item.getId())
					.itemName(item.getName())
					.amount(existingRecord.getAmount())
					.warehouseId(warehouse.getId())
					.warehouseName(warehouse.getName())
					.companyId(warehouse.getCompany().getId())
					.companyName(warehouse.getCompany().getName())
					.build();
		} else {
			// Create new record
			ItemRecord newRecord = ItemRecord.builder()
					.item(item)
					.amount(req.getAmount())
					.warehouse(warehouse)
					.build();
			
			newRecord = itemRecordRepository.save(newRecord);
			
			return ItemRecordResponse.builder()
					.id(newRecord.getId())
					.itemId(item.getId())
					.itemName(item.getName())
					.amount(newRecord.getAmount())
					.warehouseId(warehouse.getId())
					.warehouseName(warehouse.getName())
					.companyId(warehouse.getCompany().getId())
					.companyName(warehouse.getCompany().getName())
					.build();
		}
	}

	@Transactional
	public void deleteWarehouse(Integer warehouseId, String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if (!user.getValid()) {
			throw new IllegalArgumentException("User account is not active");
		}
		
		if (user.getType() != com.example.demo.model.AccountType.INVENTORY_MANAGER) {
			throw new IllegalArgumentException("Only INVENTORY_MANAGER users can delete warehouses");
		}
		
		Warehouse warehouse = warehouseRepository.findById(warehouseId)
				.orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
		
		// Check if user belongs to the same company as the warehouse
		if (!user.getCompany().getId().equals(warehouse.getCompany().getId())) {
			throw new IllegalArgumentException("You can only delete warehouses in your company");
		}
		
		// Check if warehouse has items
		if (!warehouse.getItemRecord().isEmpty()) {
			throw new IllegalArgumentException("Cannot delete warehouse with existing items. Please remove all items first.");
		}
		
		warehouseRepository.delete(warehouse);
	}

	@Transactional
	public void deleteItemFromWarehouse(Integer itemId, Integer warehouseId, String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		if (!user.getValid()) {
			throw new IllegalArgumentException("User account is not active");
		}
		
		if (user.getType() != com.example.demo.model.AccountType.INVENTORY_MANAGER) {
			throw new IllegalArgumentException("Only INVENTORY_MANAGER users can delete items from warehouses");
		}
		
		Warehouse warehouse = warehouseRepository.findById(warehouseId)
				.orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
		
		// Check if user belongs to the same company as the warehouse
		if (!user.getCompany().getId().equals(warehouse.getCompany().getId())) {
			throw new IllegalArgumentException("You can only delete items from warehouses in your company");
		}
		
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new IllegalArgumentException("Item not found"));
		
		ItemRecord itemRecord = itemRecordRepository.findByItemAndWarehouse(item, warehouse)
				.orElseThrow(() -> new IllegalArgumentException("Item not found in this warehouse"));
		
		itemRecordRepository.delete(itemRecord);
	}
}
