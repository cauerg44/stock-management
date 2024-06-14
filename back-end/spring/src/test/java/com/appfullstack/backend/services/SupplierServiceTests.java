package com.appfullstack.backend.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appfullstack.backend.dto.SupplierDTO;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.repositories.SupplierRepository;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;
import com.appfullstack.backend.tests.SupplierFactory;

@ExtendWith(SpringExtension.class)
public class SupplierServiceTests {

	@InjectMocks
	private SupplierService service;
	
	@Mock
	private SupplierRepository repository;
	
	private long existingSupplierId, nonExistingSupplierId, dependentSupplierId;
	private String supplierName;
	private Supplier supplier;
	
	@BeforeEach
	void setUp() throws Exception {
		existingSupplierId = 1L;
		nonExistingSupplierId = 2L;
		dependentSupplierId = 3L;
		
		supplierName = "Aliança ME";
		
		supplier = SupplierFactory.createSupplier(supplierName);
		
		Mockito.when(repository.findById(existingSupplierId)).thenReturn(Optional.of(supplier));
		Mockito.when(repository.findById(nonExistingSupplierId)).thenReturn(Optional.empty());
		
	}
	
	@Test
	public void findByIdShouldReturnSupplierDTOWhenIdExists() {
		
		SupplierDTO dto = service.findById(existingSupplierId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), existingSupplierId);
		Assertions.assertEquals(dto.getName(), supplier.getName());
		Assertions.assertEquals(dto.getFoundationYear(), supplier.getFoundationYear());
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingSupplierId);
		});
	}
	
	
	
}
