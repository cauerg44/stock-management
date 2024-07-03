package com.appfullstack.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appfullstack.backend.entities.User;
import com.appfullstack.backend.services.exceptions.ForbiddenException;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public void validateClientOrStockManager(Long userId) {
		User me = userService.authenticated();
		if (me.hasRole("ROLE_STOCK_MANAGER")) {
			return;
		}
		if (!me.getId().equals(userId)) {
			throw new ForbiddenException("Acess denied. Should be self or stock manager");
		}
	}
}
