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

import com.appfullstack.backend.dto.CategoryDTO;
import com.appfullstack.backend.entities.Category;
import com.appfullstack.backend.repositories.CategoryRepository;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;
import com.appfullstack.backend.tests.CategoryFactory;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	@InjectMocks
	private CategoryService service;
	
	@Mock
	private CategoryRepository repository;
	
	private long existingCategoryId, nonExistingCategoryId;;
	private String categoryName;
	private Category category;
	private CategoryDTO categoryDTO;
	private List<Category> list;
	
	@BeforeEach
	void setUp() throws Exception {
		existingCategoryId = 1L;
		nonExistingCategoryId = 2L;
		
		categoryName = "Games";
		
		category = CategoryFactory.createCategory(categoryName);
		categoryDTO = new CategoryDTO(category);
		list = new ArrayList<>(List.of(category));
		
		Mockito.when(repository.findById(existingCategoryId)).thenReturn(Optional.of(category));
		Mockito.when(repository.findById(nonExistingCategoryId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.findAll()).thenReturn(list);
		
		Mockito.when(repository.save(any())).thenReturn(category);
	}
	
	@Test
	public void findByIdShouldReturnCategoryDTOWhenIdExists() {
		
		CategoryDTO dto = service.findById(existingCategoryId);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), existingCategoryId);
		Assertions.assertEquals(dto.getName(), category.getName());
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingCategoryId);
		});
	}
	
	@Test
	public void findAllShouldReturnListOfCategoryDTO() {
		
		List<CategoryDTO> list = service.findAll();
		
		Assertions.assertNotNull(list);
		Assertions.assertEquals(list.size(), 1);
		Assertions.assertEquals(list.iterator().next().getName(), categoryName);
	}
	
	@Test
	public void insertShouldReturnNewCategoryDTO() {
		
		CategoryDTO dto = service.insert(categoryDTO);
		
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getId(), category.getId());
		Assertions.assertEquals(dto.getName(), category.getName());
	}
}
