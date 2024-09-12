package com.appfullstack.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appfullstack.backend.dto.UserDTO;
import com.appfullstack.backend.entities.User;
import com.appfullstack.backend.projection.UserDetailsProjection;
import com.appfullstack.backend.repositories.UserRepository;
import com.appfullstack.backend.tests.UserDetailsFactory;
import com.appfullstack.backend.tests.UserFactory;
import com.appfullstack.backend.util.CustomUserUtil;

import static java.util.Optional.empty;

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
		
		Mockito.when(userRepository.findByEmail(existingUsername)).thenReturn(user);
		Mockito.when(userRepository.findByEmail(nonExistingUsername)).thenReturn(null);
	}
	
	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		
		UserDetails user = service.loadUserByUsername(existingUsername);
		
		Assertions.assertNotNull(user);
		Assertions.assertEquals(user.getUsername(), existingUsername);
	}
	
	@Test
	public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUsername);
		});
	}
	
	@Test
	public void authenticatedShouldReturnUserWhenUserExists() {
		
		Mockito.when(customUserUtil.getLoggedUsername()).thenReturn(existingUsername);
		
		User user = service.authenticated();
		
		Assertions.assertNotNull(user);
		Assertions.assertEquals(user.getUsername(), existingUsername);
	}
	
	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		
		UserService spyUserService = Mockito.spy(service);
		Mockito.doReturn(user).when(spyUserService).authenticated();
		
		UserDTO result = spyUserService.getMyself();
		
		Assertions.assertNotNull(result);;
		Assertions.assertEquals(result.getEmail(), existingUsername);
	}
	
	@Test
	public void getMeShouldThrowUsernameNotFoundExceptionWhenUserNotAuthenticated() {
		
		UserService spyUserService = Mockito.spy(service);
		Mockito.doThrow(UsernameNotFoundException.class).when(spyUserService).authenticated();
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			UserDTO result = spyUserService.getMyself();
		});
	}
}
