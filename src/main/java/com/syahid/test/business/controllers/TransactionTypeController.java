package com.syahid.test.business.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syahid.test.business.dto.TransactionTypeDto;
import com.syahid.test.business.dto.ResponseDto;
import com.syahid.test.business.exceptions.AnException;
import com.syahid.test.business.models.TransactionType;
import com.syahid.test.business.services.TransactionTypeService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@RestController
@RequestMapping("/transactionType")
@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
public class TransactionTypeController {
	
	@Autowired
	TransactionTypeService transactionTypeService;

	@PostMapping
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> create(@RequestBody TransactionTypeDto param) throws AnException {
		transactionTypeService.create(param);
		return ResponseDto.success();
	}
	
	//get mapping unsupported dto
	@GetMapping
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<List<TransactionType>> getList() {
		return ResponseDto.success(transactionTypeService.getList());
	}
	
	@GetMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<TransactionType> getById(@PathVariable("id") Long id) {
		return ResponseDto.success(transactionTypeService.getById(id));
	}
	
	@PutMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> update(@PathVariable("id") Long id, @RequestBody TransactionTypeDto param) throws AnException {
		transactionTypeService.update(id, param);
		return ResponseDto.success();
	}
	
	@DeleteMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> delete(@PathVariable("id") Long id) throws AnException {
		transactionTypeService.delete(id);
		return ResponseDto.success();
	}
}
