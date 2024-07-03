package com.appfullstack.backend.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appfullstack.backend.dto.CategoryDTO;
import com.appfullstack.backend.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Categories", description = "Controller for Category")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@Operation(
		    description = "Get category by id",
		    summary = "Get category by id",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not found", responseCode = "404"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401")
		    }
		)
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyRole('ROLE_STOCK_MANAGER', 'ROLE_CLIENT')")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(
		    description = "Get all categories",
		    summary = "List all categories",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401")
		    }
		)
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyRole('ROLE_STOCK_MANAGER', 'ROLE_CLIENT')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> categories = service.findAll();
		return ResponseEntity.ok(categories);
	}
	
	@Operation(
		    description = "Create a new category",
		    summary = "Create a new category",
		    responses = {
		         @ApiResponse(description = "Created", responseCode = "201"),
		         @ApiResponse(description = "Bad Request", responseCode = "400"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ROLE_STOCK_MANAGER')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@Operation(
		    description = "Update a category",
		    summary = "Update a category",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not found", responseCode = "404"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ROLE_STOCK_MANAGER')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(
		    description = "Delete a category",
		    summary = "Delete a category",
		    responses = {
		         @ApiResponse(description = "Sucess", responseCode = "204"),
		         @ApiResponse(description = "Bad request", responseCode = "400"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Not found", responseCode = "404"),
		    }
		)
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ROLE_STOCK_MANAGER')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
