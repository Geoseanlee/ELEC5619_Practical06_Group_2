package com.example.demo.repository;

import com.example.demo.model.Item;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	Optional<Item> findByName(String name);
}
