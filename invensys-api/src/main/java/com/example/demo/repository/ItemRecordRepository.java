package com.example.demo.repository;

import com.example.demo.model.Item;
import com.example.demo.model.ItemRecord;
import com.example.demo.model.Warehouse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRecordRepository extends JpaRepository<ItemRecord, Integer> {
	Optional<ItemRecord> findByItemAndWarehouse(Item item, Warehouse warehouse);
}
