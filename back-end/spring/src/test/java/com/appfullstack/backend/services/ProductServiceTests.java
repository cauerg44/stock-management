package com.appfullstack.backend.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appfullstack.backend.dto.ProductDTO;
import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.entities.Product;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.repositories.CategoryRepository;
import com.appfullstack.backend.repositories.ProductRepository;
import com.appfullstack.backend.repositories.SupplierRepository;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;
import com.appfullstack.backend.tests.ProductFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	@Mock
	private SupplierRepository supplierRepository;

	@Mock
	private CategoryRepository categoryRepository;

	private long existingProductId, nonExistingProductId, dependentProductId;
	private String productName;
	private Category category;
	private Supplier supplier;
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
		supplier = product.getSupplier();
		category = product.getCategories().iterator().next();

		page = new PageImpl<>(List.of(product));

		Mockito.when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
		
		Mockito.when(repository.save(any())).thenReturn(product);
		
		Mockito.when(repository.getReferenceById(existingProductId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(supplierRepository.findById(supplier.getId())).thenReturn(Optional.of(supplier));
		Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
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

	@Test
	public void findAllShouldReturnPagedProductDTO() {
		
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<ProductDTO> result = service.findAll(pageable);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getName(), productName);
	}

	@Test
	public void insertShouldReturnProductDTO() {
		
		ProductDTO result = service.insert(productDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), product.getId());
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdDoesNotExists() {
		
		ProductDTO dto = service.update(existingProductId, productDTO);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), existingProductId);
		Assertions.assertEquals(dto.getName(), productDTO.getName());
	}
	
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingProductId, productDTO);
		});
	}
}
