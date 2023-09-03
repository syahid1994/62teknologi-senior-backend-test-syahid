package com.syahid.test.business.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syahid.test.business.models.Business;

public interface BusinessRepository extends JpaRepository<Business, String> {
	
	@Query("select count(1) from Business where alias = ?1")
	long countByAlias(String alias);
	
	@Query("select count(1) from Business where alias = ?1 and id != ?2")
	long countByAliasAndIdNotEquals(String alias, String id);

	Page<Business> findAll(Specification<Business> specification, Pageable pageable);
}
