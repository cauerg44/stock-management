package com.appfullstack.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appfullstack.backend.entities.User;
import com.appfullstack.backend.projection.UserDetailsProjection;
import com.appfullstack.backend.repositories.UserRepository;
import com.appfullstack.backend.tests.UserDetailsFactory;
import com.appfullstack.backend.tests.UserFactory;
import com.appfullstack.backend.util.CustomUserUtil;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private CustomUserUtil customUserUtil;
	
	private String existingUsername, nonExistingUsername;
	private User user;
	private List<UserDetailsProjection> userDetails;
	
	@BeforeEach
	void setUp() throws Exception {
		existingUsername = "valid@gmail.com";
		nonExistingUsername = "invalid@gmail.com";
		
		user = UserFactory.createCustomClientUser(1L, existingUsername);
		
		userDetails = UserDetailsFactory.createCustomStockManagerClientUser(existingUsername);
		
		Mockito.when(userRepository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
		Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());
		
		Mockito.when(userRepository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
	}
	
	
}
