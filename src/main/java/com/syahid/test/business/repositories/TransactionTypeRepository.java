package com.syahid.test.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syahid.test.business.models.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
	
	@Query("select count(1) from TransactionType where name = ?1")
	long countByName(String name);
	
	@Query("select count(1) from TransactionType where name = ?1 and id != ?2")
	long countByNameAndIdNotEquals(String name, Long id);
}
