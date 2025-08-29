package com.example.demo.repository;

import com.example.demo.model.Warehouse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
	Optional<Warehouse> findByName(String name);
}
