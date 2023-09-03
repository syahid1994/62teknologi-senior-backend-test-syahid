package com.syahid.test.business.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syahid.test.business.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query("select count(1) from Category where alias = ?1")
	long countByAlias(String alias);
	
	@Query("select count(1) from Category where alias = ?1 and id != ?2")
	long countByAliasAndIdNotEquals(String alias, Long id);

	Page<Category> findAll(Specification<Category> specification, Pageable pageable);
}
