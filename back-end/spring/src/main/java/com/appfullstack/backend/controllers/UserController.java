package com.appfullstack.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appfullstack.backend.dto.UserDTO;
import com.appfullstack.backend.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@PreAuthorize("hasAnyRole('ROLE_STOCK_MANAGER', 'ROLE_CLIENT')")
	@GetMapping(value = "/me")
	public ResponseEntity<UserDTO> getMyself() {
		UserDTO dto = service.getMyself();
		return ResponseEntity.ok(dto);
	}
}








