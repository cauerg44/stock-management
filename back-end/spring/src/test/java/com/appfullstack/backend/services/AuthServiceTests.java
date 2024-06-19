package com.appfullstack.backend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appfullstack.backend.entities.User;
import com.appfullstack.backend.services.exceptions.ForbiddenException;
import com.appfullstack.backend.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

	@InjectMocks
	private AuthService service;
	
	@Mock
	private UserService userService;
	
	private User admin, selfClient, otherClient;
	
	@BeforeEach
	void setUp() throws Exception {
		admin = UserFactory.createStockManagerUser();
		selfClient = UserFactory.createCustomClientUser(1L, "Bob");
		otherClient = UserFactory.createCustomClientUser(2L, "Ana");
	}
	
	@Test
	public void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {
		
		Mockito.when(userService.authenticated()).thenReturn(admin);
		
		Long userId = admin.getId();
		
		Assertions.assertDoesNotThrow(() -> {
			service.validateClientOrStockManager(userId);
		});
	}
	
	@Test
	public void validateSelfOrAdminShouldDoNothingWhenSelfLogged() {
		
		Mockito.when(userService.authenticated()).thenReturn(selfClient);
		
		Long userId = selfClient.getId();
		
		Assertions.assertDoesNotThrow(() -> {
			service.validateClientOrStockManager(userId);
		});
	}
	
	@Test
	public void validateSelfOrAdminThrowsForbiddenExceptionWhenClientOtherLogged() {
		
		Mockito.when(userService.authenticated()).thenReturn(selfClient);
		
		Long userId = otherClient.getId();
		
		Assertions.assertThrows(ForbiddenException.class, () -> {
			service.validateClientOrStockManager(userId);
		});
	}
}
