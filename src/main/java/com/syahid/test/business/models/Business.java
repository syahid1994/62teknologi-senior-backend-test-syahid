package com.syahid.test.business.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "business")
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Business {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String alias;
    private String name;
    private String imageUrl;
    private boolean isClosed;
    private String url;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    @JoinTable(name = "business_categories", 
    joinColumns = { @JoinColumn(name = "business_id") }, 
    inverseJoinColumns = { @JoinColumn(name = "category_id") })
    private Set<Category> categories = new HashSet<>();
    
    @Embedded
    private Coordinates coordinates = new Coordinates();
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    @JoinTable(name = "business_transaction_types", 
    joinColumns = { @JoinColumn(name = "business_id") }, 
    inverseJoinColumns = { @JoinColumn(name = "transaction_type_id") })
    private Set<TransactionType> transactionTypes = new HashSet<>();
    
    private Integer priceLevel;
    
    @Embedded
    private Location location = new Location();
    private String phone;
    private String displayPhone;
	
	@CreatedDate
	private Date createdAt;
	@LastModifiedDate
	private Date updatedAt;
}
