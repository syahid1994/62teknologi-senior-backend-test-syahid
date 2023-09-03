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

import com.syahid.test.business.dto.CategoryDto;
import com.syahid.test.business.dto.ResponseDto;
import com.syahid.test.business.dto.ResponsePaginationDto;
import com.syahid.test.business.exceptions.AnException;
import com.syahid.test.business.models.Category;
import com.syahid.test.business.services.CategoryService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@RestController
@RequestMapping("/category")
@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	@PostMapping
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> create(@RequestBody CategoryDto param) throws AnException {
		categoryService.create(param);
		return ResponseDto.success();
	}
	
	//get mapping unsupported dto
	@GetMapping
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponsePaginationDto<List<Category>> getList(@RequestParam(required = false, defaultValue = "")String title, 
			@RequestParam(required = false, defaultValue = "createdAt")String sortBy, 
			@RequestParam(required = false, defaultValue = "desc")String sortType,
			@RequestParam(required = false, defaultValue = "1")Integer page,
			@RequestParam(required = false, defaultValue = "10")Integer limit) {
		Page<Category> data = categoryService.getList(title, sortBy, sortType, page, limit);
		return new ResponsePaginationDto(data);
	}
	
	@GetMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Category> getById(@PathVariable("id") Long id) {
		return ResponseDto.success(categoryService.getById(id));
	}
	
	@PutMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> update(@PathVariable("id") Long id, @RequestBody CategoryDto param) throws AnException {
		categoryService.update(id, param);
		return ResponseDto.success();
	}
	
	@DeleteMapping("/{id}")
	@SecurityRequirements(value = {@SecurityRequirement(name = "bearerAuth")})
	public ResponseDto<Object> delete(@PathVariable("id") Long id) throws AnException {
		categoryService.delete(id);
		return ResponseDto.success();
	}
}
