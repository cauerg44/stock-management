package com.appfullstack.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.CategoryDTO;
import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> categories = repository.findAll();
		return categories.stream().map(cat -> new CategoryDTO(cat)).toList();
	}
}
