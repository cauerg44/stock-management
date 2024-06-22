package com.appfullstack.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.appfullstack.backend.enums.Sector;
import com.appfullstack.backend.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SupplierControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
		
		supplier = new Supplier(1L, "Larshooping", "2345-1231", 2000, Sector.HOME_FUNITURE);
        supplierDTO = new SupplierDTO();
        supplierDTO.setName("Future company");
        supplierDTO.setContactInfo("futurecompany@example.com");
        supplierDTO.setFoundationYear(2024);
        supplierDTO.setSector(Sector.TECHNOLOGY);
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
	
	@Test
	public void findAllShouldReturnListOfSupplierDTOWhenStockManagerLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/suppliers")
						.header("Authorization", "Bearer " + stockManagerToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].id").value(1L));
        result.andExpect(jsonPath("$[0].name").value("Tech Supplier Inc."));
        result.andExpect(jsonPath("$[0].contactInfo").value("techsupplier@example.com"));
        result.andExpect(jsonPath("$[0].foundationYear").value(2001));
        result.andExpect(jsonPath("$[0].sector").value("TECHNOLOGY"));
	}
	
	@Test
	public void findAllShouldReturnListOfSupplierDTOWhenClientLogged() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/suppliers")
						.header("Authorization", "Bearer " + clientToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].id").value(1L));
        result.andExpect(jsonPath("$[0].name").value("Tech Supplier Inc."));
        result.andExpect(jsonPath("$[0].contactInfo").value("techsupplier@example.com"));
        result.andExpect(jsonPath("$[0].foundationYear").value(2001));
        result.andExpect(jsonPath("$[0].sector").value("TECHNOLOGY"));
	}
	
	@Test
	public void findAllShouldReturnUnauthorizedWhenTokenIsInvalid() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/suppliers")
						.header("Authorization", "Bearer " + invalidToken)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void insertShouldReturnSupplierDTOCreatedWhenLoggedAsStockManager() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(supplierDTO);
		
		ResultActions result =
				mockMvc.perform(post("/suppliers")
						.header("Authorization", "Bearer " + stockManagerToken)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").value(4L));
		result.andExpect(jsonPath("$.name").value("Future company"));
		result.andExpect(jsonPath("$.contactInfo").value("futurecompany@example.com"));
		result.andExpect(jsonPath("$.foundationYear").value(2024));
		result.andExpect(jsonPath("$.sector").value("TECHNOLOGY"));
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenStockManagerLoggedAndInvalidName() throws Exception {
		
		supplier.setName("er");
		supplierDTO = new SupplierDTO(supplier);
		
		String jsonBody = objectMapper.writeValueAsString(supplierDTO);
		
		ResultActions result = 
				mockMvc.perform(post("/suppliers")
					.header("Authorization", "Bearer " + stockManagerToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenStockManagerLoggedAndInvalidContactInfo() throws Exception {
		
		supplier.setContactInfo("12");
		supplierDTO = new SupplierDTO(supplier);
		
		String jsonBody = objectMapper.writeValueAsString(supplierDTO);
		
		ResultActions result = 
				mockMvc.perform(post("/suppliers")
					.header("Authorization", "Bearer " + stockManagerToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenStockManagerLoggedAndInvalidFoundationYear() throws Exception {
		
		supplier.setFoundationYear(0);
		supplierDTO = new SupplierDTO(supplier);
		
		String jsonBody = objectMapper.writeValueAsString(supplierDTO);
		
		ResultActions result = 
				mockMvc.perform(post("/suppliers")
					.header("Authorization", "Bearer " + stockManagerToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenStockManagerLoggedAndInvalidSector() throws Exception {
		
		supplier.setSector(null);
		supplierDTO = new SupplierDTO(supplier);
		
		String jsonBody = objectMapper.writeValueAsString(supplierDTO);
		
		ResultActions result = 
				mockMvc.perform(post("/suppliers")
					.header("Authorization", "Bearer " + stockManagerToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(supplierDTO);
		
		ResultActions result = 
				mockMvc.perform(post("/suppliers")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(supplierDTO);
		
		ResultActions result =
				mockMvc.perform(post("/suppliers")
					.header("Authorization", "Bearer " + invalidToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
}
