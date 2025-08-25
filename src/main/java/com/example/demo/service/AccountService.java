package com.example.demo.service;

import com.example.demo.model.Company;
import com.example.demo.model.Level;
import com.example.demo.model.User;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.dto.AccountResponse;
import com.example.demo.web.dto.CreateAccountRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Transactional
	public AccountResponse createAccount(CreateAccountRequest req) {
		userRepository.findByUserName(req.getUserName()).ifPresent(u -> {
			throw new IllegalArgumentException("Username already exists");
		});

		Company company = resolveCompany(req);
		boolean createdNewCompany = req.getCompanyId() == null; // if null, we created or reused by name

		User user = User.builder()
				.userName(req.getUserName())
				.password(passwordEncoder.encode(req.getPassword()))
				.type(req.getType())
				.level(createdNewCompany ? Level.ADMIN : Level.NORMAL)
				.valid(createdNewCompany) // Admin users are valid by default, normal users need approval
				.company(company)
				.build();
		user = userRepository.save(user);

		return AccountResponse.builder()
				.id(user.getId())
				.userName(user.getUserName())
				.type(user.getType())
				.level(user.getLevel())
				.valid(user.getValid())
				.companyId(company.getId())
				.companyName(company.getName())
				.build();
	}

	private Company resolveCompany(CreateAccountRequest req) {
		if (req.getCompanyId() != null) {
			Optional<Company> existing = companyRepository.findById(req.getCompanyId());
			return existing.orElseThrow(() -> new IllegalArgumentException("Company not found"));
		}
		if (req.getCompanyName() == null || req.getCompanyName().isBlank()) {
			throw new IllegalArgumentException("companyName required when companyId is not provided");
		}
		// ensure unique by name or create new
		return companyRepository.findByName(req.getCompanyName())
				.orElseGet(() -> companyRepository.save(Company.builder().name(req.getCompanyName()).build()));
	}
}


