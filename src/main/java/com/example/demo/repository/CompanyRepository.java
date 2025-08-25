package com.example.demo.repository;

import com.example.demo.model.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	Optional<Company> findByName(String name);
}


