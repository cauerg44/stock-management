package com.appfullstack.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appfullstack.backend.dto.UserDTO;
import com.appfullstack.backend.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Users", description = "Controller for User")
public class UserController {

	@Autowired
	private UserService service;
	
	@Operation(
		    description = "Get user logged",
		    summary = "Get user logged",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401")
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_STOCK_MANAGER', 'ROLE_CLIENT')")
	@GetMapping(value = "/me", produces =  "application/json")
	public ResponseEntity<UserDTO> getMyself() {
		UserDTO dto = service.getMyself();
		return ResponseEntity.ok(dto);
	}
}








