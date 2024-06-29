package com.appfullstack.backend.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.appfullstack.backend.dto.SupplierDTO;
import com.appfullstack.backend.services.SupplierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/suppliers")
@Tag(name = "Suppliers", description = "Controller for Supplier")
public class SupplierController {

	@Autowired
	private SupplierService service;
	
	@Operation(
		    description = "Get all suppliers",
		    summary = "List all suppliers",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401")
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_STOCK_MANAGER', 'ROLE_CLIENT')")
	@GetMapping(produces =  "application/json")
	public ResponseEntity<List<SupplierDTO>> findAll(){
		List<SupplierDTO> suppliers = service.findAll();
		return ResponseEntity.ok(suppliers);
	}
	
	@Operation(
		    description = "Get supplier by id",
		    summary = "Get supplier by id",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not found", responseCode = "404"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401")
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_STOCK_MANAGER', 'ROLE_CLIENT')")
	@GetMapping(value = "/{id}", produces =  "application/json")
	public ResponseEntity<SupplierDTO> findById(@PathVariable Long id) {
		SupplierDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(
		    description = "Create a new supplier",
		    summary = "Create a new supplier",
		    responses = {
		         @ApiResponse(description = "Created", responseCode = "201"),
		         @ApiResponse(description = "Bad Request", responseCode = "400"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@PreAuthorize("hasRole('ROLE_STOCK_MANAGER')")
	@PostMapping(produces =  "application/json")
	public ResponseEntity<SupplierDTO> insert(@Valid @RequestBody SupplierDTO dto) {
		dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
	}
	
	@Operation(
		    description = "Update a supplier",
		    summary = "Update a supplier",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not found", responseCode = "404"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@PreAuthorize("hasRole('ROLE_STOCK_MANAGER')")
	@PutMapping(value = "/{id}", produces =  "application/json")
	public ResponseEntity<SupplierDTO> update(@PathVariable Long id, @Valid @RequestBody SupplierDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(
		    description = "Delete a supplier",
		    summary = "Delete a supplier",
		    responses = {
		         @ApiResponse(description = "Sucess", responseCode = "204"),
		         @ApiResponse(description = "Bad request", responseCode = "400"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Not found", responseCode = "404"),
		    }
		)
	@PreAuthorize("hasRole('ROLE_STOCK_MANAGER')")
	@DeleteMapping(value = "/{id}", produces =  "application/json")
	public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
