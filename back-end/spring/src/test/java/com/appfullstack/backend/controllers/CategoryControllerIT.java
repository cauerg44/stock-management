package com.appfullstack.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.ProductDTO;
import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.entities.Product;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.enums.Rating;
import com.appfullstack.backend.enums.Sector;
import com.appfullstack.backend.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	private String clientUsername, clientPassword, stockManagerUsername, stockManagerPassword;
	private String clientToken, stockManagerToken, invalidToken;;
	private Long existingCategoryId, nonExistingCategoryId, dependentId;
	private String categoryName;
	
	@BeforeEach
	void setUp () throws Exception {
		
		clientUsername = "junior@gmail.com";
		clientPassword = "app-2024";
		stockManagerUsername = "caue@outlook.com";
		stockManagerPassword = "app-2024";
		
		categoryName = "Furtunire";
		
		existingCategoryId = 1L;
		nonExistingCategoryId = 404L;
		dependentId = 3L;
		
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		stockManagerToken = tokenUtil.obtainAccessToken(mockMvc, stockManagerUsername, stockManagerPassword);
		invalidToken = stockManagerPassword + "invalid"; // Simulating an invalid token
	}
	
	@Test
	public void findByIdShouldReturnCategoryDTOWhenIdExistsAndClientLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/categories/{id}", existingCategoryId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Electronics"));
	}
	
	@Test
	public void findByIdShouldReturnCategoryDTOWhenIdExistsAndStockManagerLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/categories/{id}", existingCategoryId)
						.header("Authorization", "Bearer " + stockManagerToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Electronics"));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/categories/{id}", nonExistingCategoryId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnUnauthorizedWhenIdExistsAndInvalidToken() throws Exception {
		
		ResultActions result =
					mockMvc.perform(get("/categories{id}", existingCategoryId)
							.header("Authorization", "Bearer " + invalidToken)
							.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
	
	
	
	
	
}
