package com.appfullstack.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.SupplierDTO;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.repositories.SupplierRepository;
import com.appfullstack.backend.services.exceptions.DatabaseException;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository repository;
	
	@Transactional(readOnly = true)
	public List<SupplierDTO> findAll(){
		List<Supplier> suppliers = repository.findAll();
		return suppliers.stream().map(sup -> new SupplierDTO(sup)).toList();
	}
	
	@Transactional(readOnly = true)
	public SupplierDTO findById(Long id) {
		Supplier supplier = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Resource not found."));
		return new SupplierDTO(supplier);
	}
	
	@Transactional
	public SupplierDTO insert(SupplierDTO dto) {
		Supplier entity = new Supplier();
		dtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new SupplierDTO(entity);
	}
	
	@Transactional
	public SupplierDTO update(Long id, SupplierDTO dto) {
		try {
			Supplier entity = repository.getReferenceById(id);
			dtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new SupplierDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resource not found.");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Resource not found.");
		}
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation exception.");
		}
	}
	
	public void dtoToEntity(SupplierDTO dto, Supplier entity) {
		entity.setName(dto.getName());
		entity.setContactInfo(dto.getContactInfo());
		entity.setFoundationYear(dto.getFoundationYear());
		entity.setSector(dto.getSector());
	}
}
