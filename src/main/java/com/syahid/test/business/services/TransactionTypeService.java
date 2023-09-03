package com.syahid.test.business.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syahid.test.business.dto.TransactionTypeDto;
import com.syahid.test.business.exceptions.AnException;
import com.syahid.test.business.models.TransactionType;
import com.syahid.test.business.repositories.TransactionTypeRepository;

import jakarta.persistence.EntityManager;

@Service
public class TransactionTypeService {

	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public void create(TransactionTypeDto param) throws AnException {
		if (transactionTypeRepository.countByName(param.getName()) > 0l) {
			throw new AnException(Constant.NAME_EXIST);
		}
		TransactionType transactionType = new TransactionType();
		transactionType.setName(param.getName());
		transactionTypeRepository.save(transactionType);
	}
	
	public List<TransactionType> getList() {
		Sort sort = Sort.by(Order.asc("name"));
		return transactionTypeRepository.findAll(sort);
	}
	
	public TransactionType getById(Long id) {
		return transactionTypeRepository.findById(id).orElse(null);
	}
	
	public void update(Long id, TransactionTypeDto param) throws AnException {
		if (transactionTypeRepository.countByNameAndIdNotEquals(param.getName(), id) > 0l) {
			throw new AnException(Constant.NAME_EXIST);
		}
		TransactionType transactionType = transactionTypeRepository.findById(id).orElse(null);
		if (transactionType == null) {
			throw new AnException(Constant.DATA_NOT_FOUND);
		}
		transactionType.setName(param.getName());
		transactionTypeRepository.save(transactionType);
	}
	
	public void delete(Long id) throws AnException {
		TransactionType transactionType = transactionTypeRepository.findById(id).orElse(null);
		if (transactionType == null) {
			throw new AnException(Constant.DATA_NOT_FOUND);
		}
		transactionTypeRepository.delete(transactionType);
	}
	
	private static class Constant {
		
		private static String NAME_EXIST = "Name exist";
		private static String DATA_NOT_FOUND = "Data not found";
	}
}
