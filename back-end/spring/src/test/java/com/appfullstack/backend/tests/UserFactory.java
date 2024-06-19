package com.appfullstack.backend.tests;

import com.appfullstack.backend.entities.Role;
import com.appfullstack.backend.entities.User;

public class UserFactory {

	public static User createClientUser() {
		User user = new User(1L, "Maria", "maria@gmail.com", "NEW_PASSWORD");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
	public static User createStockManagerUser() {
		User user = new User(2L, "Alex", "alex@gmail.com", "NEW_PASSWORD");
		user.addRole(new Role(2L, "ROLE_STOCK_MANAGER"));
		return user;
	}
	
	public static User createCustomClientUser(Long id, String username) {
		User user = new User(id, "Maria", username, "NEW_PASSWORD");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
	public static User createCustomStockManager(Long id, String username) {
		User user = new User(id, "Alex", username, "NEW_PASSWORD");
		user.addRole(new Role(2L, "ROLE_STOCK_MANAGER"));
		return user;
	}
}
