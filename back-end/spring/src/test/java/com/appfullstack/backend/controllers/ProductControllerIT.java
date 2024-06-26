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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
public class ProductControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	private String clientUsername, clientPassword, stockManagerUsername, stockManagerPassword;
	private String clientToken, stockManagerToken, invalidToken;
	private Long existingProductId, nonExistingProductId, dependentProductId;
	private String productName;
	
	private Product product;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		
		clientUsername = "everton@gmail.com";
		clientPassword = "app-2024";
		stockManagerUsername = "caue@outlook.com";
		stockManagerPassword = "app-2024";
		
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		stockManagerToken = tokenUtil.obtainAccessToken(mockMvc, stockManagerUsername, stockManagerPassword);
		invalidToken = stockManagerToken + clientToken + "error"; // Simulating an invalid token.
		
		Category category = new Category(2L, null);
		Supplier supplier = new Supplier(4L, "Closet Ltda", "78920-04842", 1997, Sector.HOME_FUNITURE);
		product = new Product(null, "Freezer", 430.00, LocalDate.parse("2023-06-24"), Rating.GOOD, "Keep products conserved 100%");
		product.getCategories().add(category);
		product.setSupplier(supplier);
		
		existingProductId = 1L;
		nonExistingProductId = 404L;
		dependentProductId = 3L;
	}
	
	@Test
	public void searchByNameShouldReturnPageWhenProductNameParamIsEmptyAndStockManagerLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products")
					.header("Authorization", "Bearer " + stockManagerToken)
					.accept(MediaType.APPLICATION_JSON))
					.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(1L));
		result.andExpect(jsonPath("$.content[0].name").value("Smartphone X"));
		result.andExpect(jsonPath("$.content[0].price").value(11345.99));
		result.andExpect(jsonPath("$.content[0].manufactureDate").value("2023-01-15"));
		result.andExpect(jsonPath("$.content[0].rating").value("EXCELENT"));
	}
	
	@Test
	public void searchByNameShouldReturnPageWhenProductNameParamIsEmptyAndClientLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products")
					.header("Authorization", "Bearer " + clientToken)
					.accept(MediaType.APPLICATION_JSON))
					.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(1L));
		result.andExpect(jsonPath("$.content[0].name").value("Smartphone X"));
		result.andExpect(jsonPath("$.content[0].price").value(11345.99));
		result.andExpect(jsonPath("$.content[0].manufactureDate").value("2023-01-15"));
		result.andExpect(jsonPath("$.content[0].rating").value("EXCELENT"));
	}
	
	@Test
	public void searchByNameShouldReturnPageWhenProductNameParamIsNotEmptyAndStockManagerLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products?name={productName}", productName)
					.header("Authorization", "Bearer " + stockManagerToken)
					.accept(MediaType.APPLICATION_JSON))
					.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(1L));
		result.andExpect(jsonPath("$.content[0].name").value("Smartphone X"));
		result.andExpect(jsonPath("$.content[0].price").value(11345.99));
		result.andExpect(jsonPath("$.content[0].manufactureDate").value("2023-01-15"));
		result.andExpect(jsonPath("$.content[0].rating").value("EXCELENT"));
	}
	
	@Test
	public void searchByNameShouldReturnPageWhenProductNameParamIsNotEmptyAndClientLogged() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products?name={productName}", productName)
					.header("Authorization", "Bearer " + clientToken)
					.accept(MediaType.APPLICATION_JSON))
					.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(1L));
		result.andExpect(jsonPath("$.content[0].name").value("Smartphone X"));
		result.andExpect(jsonPath("$.content[0].price").value(11345.99));
		result.andExpect(jsonPath("$.content[0].manufactureDate").value("2023-01-15"));
		result.andExpect(jsonPath("$.content[0].rating").value("EXCELENT"));
	}
	
	@Test
	public void searchByNameShouldReturnUnauthorizedWhenTokenIsInvalid() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products")
					.header("Authorization", "Bearer " + invalidToken)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExistsAndStockManagerLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", existingProductId)
						.header("Authorization", "Bearer " + stockManagerToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Smartphone X"));
		result.andExpect(jsonPath("$.price").value(11345.99));
		result.andExpect(jsonPath("$.manufactureDate").value("2023-01-15"));
		result.andExpect(jsonPath("$.rating").value("EXCELENT"));
		result.andExpect(jsonPath("$.supplier.name").value("Tech Supplier Inc."));
		result.andExpect(jsonPath("$.supplier.foundationYear").value(2001));
		result.andExpect(jsonPath("$.supplier.name").value("Tech Supplier Inc."));
		result.andExpect(jsonPath("$.categories").exists());
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExistsAndClientLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", existingProductId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Smartphone X"));
		result.andExpect(jsonPath("$.price").value(11345.99));
		result.andExpect(jsonPath("$.manufactureDate").value("2023-01-15"));
		result.andExpect(jsonPath("$.rating").value("EXCELENT"));
		result.andExpect(jsonPath("$.supplier.name").value("Tech Supplier Inc."));
		result.andExpect(jsonPath("$.supplier.foundationYear").value(2001));
		result.andExpect(jsonPath("$.supplier.name").value("Tech Supplier Inc."));
		result.andExpect(jsonPath("$.categories").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsAndStockManagerLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", nonExistingProductId)
						.header("Authorization", "Bearer " + stockManagerToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExistsAndClientLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", nonExistingProductId)
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturnUnauthorizedWhenTokenIsInvalid() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/products/{id}", existingProductId)
						.header("Authorization", "Bearer " + invalidToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
}
