package com.syahid.test.business.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syahid.test.business.dto.CategoryDto;
import com.syahid.test.business.exceptions.AnException;
import com.syahid.test.business.models.Category;
import com.syahid.test.business.repositories.CategoryRepository;

import jakarta.persistence.EntityManager;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void create(CategoryDto param) throws AnException {
		if (!param.getAlias().matches("[a-zA-Z0-9-]*")) {
			throw new AnException(Constant.INVALID_ALIAS);
		}
		if (categoryRepository.countByAlias(param.getAlias()) > 0l) {
			throw new AnException(Constant.ALIAS_EXIST);
		}
		Category category = new Category();
		category.setAlias(param.getAlias().toLowerCase());
		category.setTitle(param.getTitle());
		categoryRepository.save(category);
	}
	
	public Page<Category> getList(String title, String sortBy, String sortType, Integer page, Integer limit) {
		if (sortBy.equals("")) {
			sortBy = Constant.DEFAULT_SORT_BY;
		}
		if (sortType.equals("")) {
			sortType = Constant.DEFAULT_SORT_TYPE;
		}
		Sort sort = Sort.by(Direction.fromString(sortType), sortBy);
		Pageable pageable = PageRequest.of((page - 1), limit, sort);
		Specification<Category> specification = buildSpecification(title);
		if (specification == null) {
			return categoryRepository.findAll(pageable);
		} else {
			return categoryRepository.findAll(specification, pageable);
		}
	}
	
	public Specification<Category> buildSpecification(String title) {
		return (root, query, builder) -> {
			if (!title.equals("")) {
				return builder.like(
						builder.lower(root.get("title")), 
						title.toLowerCase()
						);
			}
			return null;
		};
	}
	
	public Category getById(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}
	
	public void update(Long id, CategoryDto param) throws AnException {
		if (!param.getAlias().matches("[a-zA-Z0-9-]*")) {
			throw new AnException(Constant.INVALID_ALIAS);
		}
		if (categoryRepository.countByAliasAndIdNotEquals(param.getAlias(), id) > 0l) {
			throw new AnException(Constant.ALIAS_EXIST);
		}
		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			throw new AnException(Constant.DATA_NOT_FOUND);
		}
		category.setAlias(param.getAlias().toLowerCase());
		category.setTitle(param.getTitle());
		categoryRepository.save(category);
	}
	
	public void delete(Long id) throws AnException {
		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			throw new AnException(Constant.DATA_NOT_FOUND);
		}
		categoryRepository.delete(category);
	}
	
	private static class Constant {
		
		private static String INVALID_ALIAS = "Invalid Alias";
		private static String ALIAS_EXIST = "Alias exist";
		private static String DATA_NOT_FOUND = "Data not found";
		
		private static String DEFAULT_SORT_BY = "createdAt";
		private static String DEFAULT_SORT_TYPE = "asc";
	}
}
