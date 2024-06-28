package com.appfullstack.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.appfullstack.backend.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	private String clientUsername, clientPassword, stockManagerUsername, stockManagerPassword;
	private String clientToken, stockManagerToken, invalidToken;
	
	@BeforeEach
	void setUp() throws Exception {
		
		clientUsername = "junior@gmail.com";
		clientPassword = "app-2024";
		stockManagerUsername = "caue@outlook.com";
		stockManagerPassword = "app-2024";
		
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		stockManagerToken = tokenUtil.obtainAccessToken(mockMvc, stockManagerUsername, stockManagerPassword);
		invalidToken = stockManagerPassword + "invalid"; // Simulating an invalid token
	}
	
	@Test
	public void getMeShouldReturnUserDTOWhenStockManagerLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/users/me")
					.header("Authorization", "Bearer " + stockManagerToken)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Cauê Garcia"));
		result.andExpect(jsonPath("$.email").value("caue@outlook.com"));
		result.andExpect(jsonPath("$.password").value("$2a$10$QrDGMknxOAoy6zVSV23RaOS94rsIj/6VpG9drqHGrI9.AIkNysCh2"));
		result.andExpect(jsonPath("$.roles").exists());
	}
	
	@Test
	public void getMeShouldReturnUserDTOWhenClientLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/users/me")
					.header("Authorization", "Bearer " + clientToken)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(3L));
		result.andExpect(jsonPath("$.name").value("Junior Ciqueira"));
		result.andExpect(jsonPath("$.email").value("junior@gmail.com"));
		result.andExpect(jsonPath("$.password").value("$2a$10$QrDGMknxOAoy6zVSV23RaOS94rsIj/6VpG9drqHGrI9.AIkNysCh2"));
		result.andExpect(jsonPath("$.roles").exists());
	}
	
	@Test
	public void getMeShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/users/me")
					.header("Authorization", "Bearer " + invalidToken)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
}