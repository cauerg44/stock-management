package com.appfullstack.backend.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appfullstack.backend.dto.ProductDTO;
import com.appfullstack.backend.entities.Product;
import com.appfullstack.backend.repositories.ProductRepository;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;
import com.appfullstack.backend.tests.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;
	
	private long existingProductId, nonExistingProductId, dependentProductId;
	private String productName;
	private Product product;
	private ProductDTO productDTO;
	private PageImpl<Product> page;

	@BeforeEach
	void setUp() throws Exception {
		existingProductId = 1L;
		nonExistingProductId = 2L;
		dependentProductId = 3L;
		
		productName = "Coffe cup";
		
		product = ProductFactory.createProduct(productName);
		productDTO = new ProductDTO(product);
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());
		
	}
	
	@Test
	public void findByIdShouldReturnProductDTO() {
		
		ProductDTO dto = service.findById(existingProductId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), existingProductId);
		Assertions.assertEquals(dto.getName(), product.getName());
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingProductId);
		});
	}
}
