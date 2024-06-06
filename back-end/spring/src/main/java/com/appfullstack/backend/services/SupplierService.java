package com.appfullstack.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.SupplierDTO;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.repositories.SupplierRepository;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository repository;
	
	@Transactional(readOnly = true)
	public SupplierDTO findById(Long id) {
		Supplier supplier = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Resource not found."));
		return new SupplierDTO(supplier);
	}
}
