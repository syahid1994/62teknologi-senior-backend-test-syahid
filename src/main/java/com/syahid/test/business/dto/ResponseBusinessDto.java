package com.syahid.test.business.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBusinessDto {
	
	private List<Business> business;
    
	@Getter
	@Setter
	public static class Business {
		@Schema(example = "jbJTF1zVmZsk4hea8hoeVA")
	    private String id;
	    
	    @Schema(example = "fork-boise")
	    private String alias;
	    
	    @Schema(example = "Fork")
	    private String name;
	    
	    @JsonProperty("image_url")
	    @Schema(example = "https://s3-media4.fl.yelpcdn.com/bphoto/Eba_r1zN_xz_aU-7c_kyrg/o.jpg")
	    private String imageUrl;
	    
	    @Schema(example = "https://www.yelp.com/biz/fork-boise?adjust_creative=DSj6I8qbyHf-Zm2fGExuug&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=DSj6I8qbyHf-Zm2fGExuug")
	    private String url;
	    
	    private Coordinates coordinates;
	    
	    @Schema(example = "[\"delivery\"]")
	    private List<String> transactionTypes;
	    
	    @Schema(example = "4")
	    private Integer priceLevel;
	    
	    private Location location;
	    
	    @Schema(example = "+12082871700")
	    private String phone;
	    
	    @JsonProperty("display_phone")
	    @Schema(example = "(208) 287-1700")
	    private String displayPhone;
	    
	    private List<Category> categories;
	
	    @Getter
	    @Setter
	    public static class Coordinates {
	        @Schema(example = "43.616389")
	        private double latitude;
	        
	        @Schema(example = "-116.203056")
	        private double longitude;
	    }
	
	    @Getter
	    @Setter
	    public static class Location {
	        @Schema(example = "199 N 8th St")
	        private String address1;
	        
	        @Schema(example = "")
	        private String address2;
	        
	        @Schema(example = "")
	        private String address3;
	        
	        @Schema(example = "Boise")
	        private String city;
	        
	        @JsonProperty("zip_code")
	        @Schema(example = "83702")
	        private String zipCode;
	        
	        @Schema(example = "US")
	        private String country;
	        
	        @Schema(example = "ID")
	        private String state;
	        
	        @JsonProperty("display_address")
	        @Schema(example = "[\"199 N 8th St\",\"Boise, ID 83702\"]")
	        private Set<String> displayAddress;
	    }
	    
	    @Getter
	    @Setter
	    public static class Category {
	    	private String alias;
	        private String title;
	    }
	}
}
