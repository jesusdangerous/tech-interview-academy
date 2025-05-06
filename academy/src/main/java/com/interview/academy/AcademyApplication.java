package com.interview.academy;

import com.interview.academy.domain.Role;
import com.interview.academy.domain.entities.User;
import com.interview.academy.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademyApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder encoder) {
//		return args -> {
//			if (!userRepository.existsByEmail("admin@example.com")) {
//				User admin = User.builder()
//						.email("admin@example.com")
//						.password(encoder.encode("admin123"))
//						.name("Administrator")
//						.role(Role.ADMIN)
//						.build();
//				userRepository.save(admin);
//			}
//		};
//	}
}
