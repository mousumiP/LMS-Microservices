package com.lms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.auth.dto.AuthResponseDto;
import com.lms.auth.service.AuthService;

@RestController
@RequestMapping("/oauth2")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/token")
	public ResponseEntity<AuthResponseDto> getToken(@RequestParam String username,
            @RequestParam String password) {
		AuthResponseDto responseDto = authService.getToken(username, password);
		return ResponseEntity.ok(responseDto);
		
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponseDto> refreshAccessToken(@RequestParam String refresh_token) {
		//return authService.refreshAccessToken(refresh_token);		
		return null;
	}
}
