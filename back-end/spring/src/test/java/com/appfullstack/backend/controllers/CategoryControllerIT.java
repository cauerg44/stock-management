package com.appfullstack.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.appfullstack.backend.dto.CategoryDTO;
import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String clientUsername, clientPassword, stockManagerUsername, stockManagerPassword;
	private String clientToken, stockManagerToken, invalidToken;;
	private Long existingCategoryId, nonExistingCategoryId, dependentId;
	private String categoryName;
	
	private Category category;
	private CategoryDTO categoryDTO;
	
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
		
		category = new Category();
        category.setName("Food");
        categoryDTO = new CategoryDTO(category);
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
	
	@Test
	public void findAllShouldReturnListOfCategoryDTOWhenStockManagerLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/categories")
						.header("Authorization", "Bearer " + stockManagerToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].id").value(1L));
        result.andExpect(jsonPath("$[0].name").value("Electronics"));
        result.andExpect(jsonPath("$[1].id").value(2L));
        result.andExpect(jsonPath("$[1].name").value("Office Supplies"));
        result.andExpect(jsonPath("$[2].id").value(3L));
        result.andExpect(jsonPath("$[2].name").value("Furniture"));
	}
	
	@Test
	public void findAllShouldReturnListOfCategoryDTOWhenClientLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/categories")
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].id").value(1L));
        result.andExpect(jsonPath("$[0].name").value("Electronics"));
        result.andExpect(jsonPath("$[1].id").value(2L));
        result.andExpect(jsonPath("$[1].name").value("Office Supplies"));
        result.andExpect(jsonPath("$[2].id").value(3L));
        result.andExpect(jsonPath("$[2].name").value("Furniture"));
	}
	
	@Test
	public void findAllShouldReturnUnauthorizedWhenTokenIsInvalid() throws Exception {
		
		ResultActions result =
					mockMvc.perform(get("/categories")
							.header("Authorization", "Bearer " + invalidToken)
							.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void insertShouldReturnCategoryDTOCreatedWhenLoggedAsStockManager() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		ResultActions result =
				mockMvc.perform(post("/categories")
						.header("Authorization", "Bearer " + stockManagerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").value(4L));
		result.andExpect(jsonPath("$.name").value("Food"));
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenStockManagerLoggedAndInvalidCategoryName() throws Exception {
		
		category.setName("er");
		categoryDTO = new CategoryDTO(category);
		
		String jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		ResultActions result =
				mockMvc.perform(post("/categories")
						.header("Authorization", "Bearer " + stockManagerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		ResultActions result = 
				mockMvc.perform(post("/categories")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void updateShouldReturnCategoryDTOWhenIdExistsAndStockManagerLogged() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		ResultActions result =
					mockMvc.perform(put("/categories/{id}", existingCategoryId)
							.header("Authorization", "Bearer " + stockManagerToken)
							.content(jsonBody)
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Food"));
	}
	
	@Test
	public void updateShouldReturnResourceNotFoundWhenIdDoesNotExistsAndStockManagerLogged() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		ResultActions result =
				mockMvc.perform(put("/categories/{id}", nonExistingCategoryId)
						.header("Authorization", "Bearer " + stockManagerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
	
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateShouldReturnUnprocessableEntityWhenIdExistsAndStockManagerLoggedAndInvalidName() throws Exception {
		
		category.setName("er");
		categoryDTO = new CategoryDTO(category);
		
		String jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		ResultActions result =
			    mockMvc.perform(put("/categories/{id}", existingCategoryId)
			            .header("Authorization", "Bearer " + stockManagerToken)
			            .content(jsonBody)
			            .contentType(MediaType.APPLICATION_JSON)
			            .accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void updateShouldReturnForbiddenWhenIdExistsAndClientLogged() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		ResultActions result = 
				mockMvc.perform(put("/categories/{id}", existingCategoryId)
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
}
