package com.example.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UsermanagementApplication {

//	@Bean
//	CommandLineRunner iniciar(UserRepository repository,
//							  PasswordEncoder encoder) {
//		return args -> {
//			if (repository.findByLogin("admin").isEmpty()) {
//				User admin = User.builder()
//						.username("admin")
//						.password(encoder.encode("admin123"))
//						.role(UserRole.ADMIN)
//						.enabled(true)
//						.build();
//
//				repository.save(admin);
//			}
//
//			if (repository.findByLogin("user").isEmpty()) {
//				User admin = User.builder()
//						.username("user")
//						.password(encoder.encode("user123"))
//						.role(UserRole.USER)
//						.enabled(true)
//						.build();
//
//				repository.save(admin);
//			}
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementApplication.class, args);
	}
	
}
