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
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.SupplierDTO;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SupplierControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	private String clientUsername, clientPassword, stockManagerUsername, stockManagerPassword;
	private String clientToken, stockManagerToken, invalidToken;
	private Long existingSupplierId, nonExistingSupplierId, dependentSupplierId;
	private String supplierName;
	
	private Supplier supplier;
	private SupplierDTO supplierDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		
		clientUsername = "junior@gmail.com";
		clientPassword = "app-2024";
		stockManagerUsername = "caue@outlook.com";
		stockManagerPassword = "app-2024";
		
		supplierName = "Aliança ME";
		
		existingSupplierId = 1L;
		nonExistingSupplierId = 404L;
		dependentSupplierId = 3L;
		
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		stockManagerToken = tokenUtil.obtainAccessToken(mockMvc, stockManagerUsername, stockManagerPassword);
		invalidToken = stockManagerPassword + "invalid"; // Simulating an invalid token
		
		
	}
	
	@Test
	public void findByIdShouldReturnSupplierDTOWhenIdExistsAndClientLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/suppliers/{id}", existingSupplierId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Tech Supplier Inc."));
		result.andExpect(jsonPath("$.contactInfo").value("techsupplier@example.com"));
		result.andExpect(jsonPath("$.foundationYear").value(2001));
		result.andExpect(jsonPath("$.sector").value("TECHNOLOGY"));
	}
	
	@Test
	public void findByIdShouldReturnSupplierDTOWhenIdExistsAndStockManagerLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/suppliers/{id}", existingSupplierId)
						.header("Authorization", "Bearer " + stockManagerToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Tech Supplier Inc."));
		result.andExpect(jsonPath("$.contactInfo").value("techsupplier@example.com"));
		result.andExpect(jsonPath("$.foundationYear").value(2001));
		result.andExpect(jsonPath("$.sector").value("TECHNOLOGY"));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExistAndClientLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/suppliers/{id}", nonExistingSupplierId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExistAndStockManagerLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/suppliers/{id}", nonExistingSupplierId)
						.header("Authorization", "Bearer " + stockManagerToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnUnauthorizedWhenTokenIsInvalid() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/suppliers/{id}", existingSupplierId)
						.header("Authorization", "Bearer " + invalidToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
}
