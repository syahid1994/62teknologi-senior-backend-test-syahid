package com.syahid.test.business.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syahid.test.business.dto.BusinessDto;
import com.syahid.test.business.dto.BusinessDto;
import com.syahid.test.business.dto.ResponseDto;
import com.syahid.test.business.dto.ResponsePaginationDto;
import com.syahid.test.business.exceptions.AnException;
import com.syahid.test.business.models.Business;
import com.syahid.test.business.services.BusinessService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@RestController
@RequestMapping("/business")
@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
public class BusinessController {

	@Autowired
	BusinessService businessService;

	@PostMapping
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> create(@RequestBody BusinessDto param) throws AnException {
		businessService.create(param);
		return ResponseDto.success();
	}
	
	//get mapping unsupported dto
	@GetMapping
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponsePaginationDto<List<Business>> getList(@RequestParam(required = false, defaultValue = "")String name,
			@RequestParam(required = false, defaultValue = "")Integer priceLevel,
			@RequestParam(required = false, defaultValue = "")String city,
			@RequestParam(required = false, defaultValue = "")String category,
			@RequestParam(required = false, defaultValue = "name")String sortBy, 
			@RequestParam(required = false, defaultValue = "desc")String sortType,
			@RequestParam(required = false, defaultValue = "1")Integer page,
			@RequestParam(required = false, defaultValue = "10")Integer limit) {
		Page<Business> data = businessService.getList(name, priceLevel, city, category, sortBy, sortType, page, limit);
		return new ResponsePaginationDto(data);
	}
	
	@GetMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Business> getById(@PathVariable("id") String id) {
		return ResponseDto.success(businessService.getById(id));
	}
	
	@PutMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> update(@PathVariable("id") String id, @RequestBody BusinessDto param) throws AnException {
		businessService.update(id, param);
		return ResponseDto.success();
	}
	
	@DeleteMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> delete(@PathVariable("id") String id) throws AnException {
		businessService.delete(id);
		return ResponseDto.success();
	}
	
}
