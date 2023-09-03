package com.syahid.test.business.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syahid.test.business.dto.LoginDto;
import com.syahid.test.business.dto.ResponseDto;
import com.syahid.test.business.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;

	@PostMapping("/generateToken")
	public ResponseDto<Object> generateToken(@RequestBody LoginDto param) {
		String token = authService.generateToken(param.getUsername());
		return ResponseDto.success(Map.of("token", token));
	}
}
