package com.appfullstack.backend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
}