package com.syahid.test.business.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Location {
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String zipCode;
    private String country;
    private String state;
    
    @ElementCollection
    @CollectionTable(name = "location_display_address", joinColumns = @JoinColumn(name = "business_id"))
    @Column(name = "display_address")
    private Set<String> displayAddress = new HashSet<>();

    // getters and setters
}
