package com.appfullstack.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appfullstack.backend.dto.EmailDTO;
import com.appfullstack.backend.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping(value = "/recover-token")
	public ResponseEntity<Void> createRecoverToken(@RequestBody EmailDTO body) {
		authService.createRecoverToken(body);
		return ResponseEntity.noContent().build();
	}
}








