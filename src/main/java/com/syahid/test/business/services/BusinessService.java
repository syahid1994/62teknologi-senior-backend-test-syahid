package com.syahid.test.business.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syahid.test.business.dto.BusinessDto;
import com.syahid.test.business.exceptions.AnException;
import com.syahid.test.business.models.Business;
import com.syahid.test.business.models.Category;
import com.syahid.test.business.models.TransactionType;
import com.syahid.test.business.repositories.BusinessRepository;
import com.syahid.test.business.repositories.CategoryRepository;
import com.syahid.test.business.repositories.TransactionTypeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;

@Service
public class BusinessService {

	@Autowired
	BusinessRepository businessRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void create(BusinessDto param) throws AnException {
		if (businessRepository.countByAlias(param.getAlias()) > 0l) {
			throw new AnException(Constant.ALIAS_EXIST);
		}
		Business business = map(new Business(), param);
		businessRepository.save(business);
	}
	
	public Business map(Business business, BusinessDto param) throws AnException {
		business.setAlias(param.getAlias());
		business.setName(param.getName());
		business.setImageUrl(param.getImageUrl());
		business.setUrl(param.getUrl());
		
		if (param.getCategories() != null) {
			for (Long categoryId: param.getCategories()) {
				Category category = categoryRepository.findById(categoryId).orElse(null);
				if (category == null) {
					throw new AnException(Constant.CATEGORY_NOT_FOUND);
				}
				business.getCategories().add(category);
			}
		}
		
		business.getCoordinates().setLatitude(param.getCoordinates().getLatitude());
		business.getCoordinates().setLongitude(param.getCoordinates().getLongitude());
		
		if (param.getTransactionTypes() != null) {
			for (Long transactionTypeId: param.getTransactionTypes()) {
				TransactionType transactionType = transactionTypeRepository.findById(transactionTypeId).orElse(null);
				if (transactionType == null) {
					throw new AnException(Constant.TRANSACTION_TYPE_NOT_FOUND);
				}
				business.getTransactionTypes().add(transactionType);
			}
		}
		
		business.setPriceLevel(param.getPriceLevel());
		business.getLocation().setAddress1(param.getLocation().getAddress1());
		business.getLocation().setAddress2(param.getLocation().getAddress2());
		business.getLocation().setAddress3(param.getLocation().getAddress3());
		business.getLocation().setCity(param.getLocation().getCity());
		business.getLocation().setZipCode(param.getLocation().getZipCode());
		business.getLocation().setCountry(param.getLocation().getCountry());
		business.getLocation().setState(param.getLocation().getState());
		business.getLocation().setDisplayAddress(param.getLocation().getDisplayAddress());
		
		return business;
	}
	
	public Page<Business> getList(String name, Integer priceLevel, String city, String category, String sortBy, String sortType, Integer page, Integer limit) {
		if (sortBy.equals("")) {
			sortBy = Constant.DEFAULT_SORT_BY;
		}
		if (sortType.equals("")) {
			sortType = Constant.DEFAULT_SORT_TYPE;
		}
		Sort sort = Sort.by(Direction.fromString(sortType), sortBy);
		Pageable pageable = PageRequest.of((page - 1), limit, sort);
		Specification<Business> specification = buildSpecification(name, priceLevel, city, category);
		if (specification == null) {
			return businessRepository.findAll(pageable);
		} else {
			return businessRepository.findAll(specification, pageable);
		}
	}
	
	public Specification<Business> buildSpecification(String name, Integer priceLevel, String city, String category) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!name.equals("")) {
				predicates.add(builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
			}
			if (priceLevel != null) {
				predicates.add(builder.equal(root.get("priceLevel"), priceLevel));
			}
			if (!city.equals("")) {
				predicates.add(builder.like(builder.lower(root.get("location").get("city")), "%" + city.toLowerCase() + "%"));
			}
			if (!category.equals("")) {
				predicates.add(builder.like(builder.lower(root.<List<Category>>get("categories").<String>get("title")), "%" + category.toLowerCase() + "%"));
			}
			if (predicates.size() == 0) {
				return null;
			}
			return builder.and(predicates.toArray(Predicate[]::new));
		};
	}
	
	public Business getById(String id) {
		return businessRepository.findById(id).orElse(null);
	}
	
	public void update(String id, BusinessDto param) throws AnException {
		if (businessRepository.countByAliasAndIdNotEquals(param.getAlias(), id) > 0l) {
			throw new AnException(Constant.ALIAS_EXIST);
		}
		Business business = businessRepository.findById(id).orElse(null);
		if (business == null) {
			throw new AnException(Constant.DATA_NOT_FOUND);
		}
		business = map(new Business(), param);
		businessRepository.save(business);
	}
	
	public void delete(String id) throws AnException {
		Business business = businessRepository.findById(id).orElse(null);
		if (business == null) {
			throw new AnException(Constant.DATA_NOT_FOUND);
		}
		businessRepository.delete(business);
	}
	
	private static class Constant {
		
		private static String ALIAS_EXIST = "Alias exist";
		private static String CATEGORY_NOT_FOUND = "Category not found";
		private static String TRANSACTION_TYPE_NOT_FOUND = "Transaction type not found";
		private static String DATA_NOT_FOUND = "Data not found";
		
		private static String DEFAULT_SORT_BY = "name";
		private static String DEFAULT_SORT_TYPE = "asc";
	}
}
