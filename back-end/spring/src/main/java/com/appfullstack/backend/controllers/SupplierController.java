package com.appfullstack.backend.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.appfullstack.backend.dto.SupplierDTO;
import com.appfullstack.backend.services.SupplierService;

@RestController
@RequestMapping(value = "/suppliers")
public class SupplierController {

	@Autowired
	private SupplierService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SupplierDTO> findById(@PathVariable Long id) {
		SupplierDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<SupplierDTO> insert(@RequestBody SupplierDTO dto) {
		dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
	}
	
	
}
