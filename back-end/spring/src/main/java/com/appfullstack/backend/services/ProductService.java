package com.appfullstack.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.CategoryDTO;
import com.appfullstack.backend.dto.ProductDTO;
import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.entities.Product;
import com.appfullstack.backend.entities.Supplier;
import com.appfullstack.backend.repositories.CategoryRepository;
import com.appfullstack.backend.repositories.ProductRepository;
import com.appfullstack.backend.repositories.SupplierRepository;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> products = repository.findAll(pageable);
		return products.map(prod -> new ProductDTO(prod));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product prod = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Resource not found."));
		return new ProductDTO(prod);
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		dtoToEntity(entity, dto);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
			dtoToEntity(entity, dto);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resource not found.");
		}
	}
	
	private void dtoToEntity(Product entity, ProductDTO dto) {
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.setDescription(dto.getDescription());
		entity.setManufactureDate(dto.getManufactureDate());
		entity.setRating(dto.getRating());
		
		if (dto.getSupplier() != null && dto.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplier().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id unknown."));
            entity.setSupplier(supplier);
        } else {
            throw new IllegalArgumentException("Supplier ID cannot be null.");
        }
        
		entity.getCategories().clear();
	    if (dto.getCategories() != null && !dto.getCategories().isEmpty()) {
	        for (CategoryDTO categoryDTO : dto.getCategories()) {
	            Category category = categoryRepository.findById(categoryDTO.getId())
	                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id unknown"));
	            entity.getCategories().add(category);
	        }
	    }
	}
}
