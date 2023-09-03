package com.syahid.test.business.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction_type")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TransactionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, 
    		cascade = {CascadeType.PERSIST, CascadeType.DETACH},
    		mappedBy = "transactionTypes"
    )
    @JsonIgnore
    private List<Business> business;
	
    @CreatedDate
	private Date createdAt;
	@LastModifiedDate
	private Date updatedAt;
}
