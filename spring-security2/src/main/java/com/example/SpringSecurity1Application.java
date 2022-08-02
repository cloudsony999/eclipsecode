package com.example;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.UserService;

@SpringBootApplication
public class SpringSecurity1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity1Application.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "user1", "user1", "123", new ArrayList<>()));
			userService.saveUser(new User(null, "user2", "user2", "123", new ArrayList<>()));
			userService.saveUser(new User(null, "user3", "user3", "123", new ArrayList<>()));
			userService.saveUser(new User(null, "user4", "user4", "123", new ArrayList<>()));

			userService.addRoleToUser("user1", "ROLE_USER");
			userService.addRoleToUser("user2", "ROLE_MANAGER");
			userService.addRoleToUser("user3", "ROLE_ADMIN");
			userService.addRoleToUser("user4", "ROLE_ADMIN");
			userService.addRoleToUser("user4", "ROLE_USER");
			userService.addRoleToUser("user4", "ROLE_SUPER_ADMIN");
		};
	}

}
