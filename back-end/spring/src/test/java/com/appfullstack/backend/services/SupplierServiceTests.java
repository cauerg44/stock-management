package com.appfullstack.backend.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
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
	
	private long existingSupplierId, nonExistingSupplierId;
	private String supplierName;
	private Supplier supplier;
	private SupplierDTO supplierDTO;
	private List<Supplier> list;
	
	@BeforeEach
	void setUp() throws Exception {
		existingSupplierId = 1L;
		nonExistingSupplierId = 2L;
		
		supplierName = "Aliança ME";
		
		supplier = SupplierFactory.createSupplier(supplierName);
		supplierDTO = new SupplierDTO(supplier);
		list = new ArrayList<>(List.of(supplier));
		
		Mockito.when(repository.findById(existingSupplierId)).thenReturn(Optional.of(supplier));
		Mockito.when(repository.findById(nonExistingSupplierId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.findAll()).thenReturn(list);
		
		Mockito.when(repository.save(any())).thenReturn(supplier);
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
	
	@Test
	public void findAllShouldReturnListOfSupplierDTO() {
		
		List<SupplierDTO> list = service.findAll();
		
		Assertions.assertNotNull(list);
		Assertions.assertEquals(list.size(), 1);
		Assertions.assertEquals(list.iterator().next().getName(), supplierName);
	}
	
	@Test
	public void insertShouldReturnSupplierDTO() {
		
		SupplierDTO dto = service.insert(supplierDTO);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), supplier.getId());
	}
}
