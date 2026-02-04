package com.example.usermanagement;

import com.example.usermanagement.model.users.Role;
import com.example.usermanagement.model.users.User;
import com.example.usermanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication

public class UsermanagementApplication {

	@Bean
	CommandLineRunner iniciar(UserRepository repository,
							  PasswordEncoder encoder) {
		return args -> {
			if (repository.findByUsername("admin").isEmpty()) {
				User admin = User.builder()
						.username("admin")
						.password(encoder.encode("admin123"))
						.role(Role.ADMIN)
						.enabled(true)
						.build();

				repository.save(admin);
			}

			if (repository.findByUsername("user").isEmpty()) {
				User admin = User.builder()
						.username("user")
						.password(encoder.encode("user123"))
						.role(Role.USER)
						.enabled(true)
						.build();

				repository.save(admin);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
	}

}
