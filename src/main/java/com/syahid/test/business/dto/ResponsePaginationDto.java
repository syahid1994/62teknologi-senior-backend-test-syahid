package com.syahid.test.business.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponsePaginationDto<T> {

	private int status;
	private String message;
	private List<T> data;
	private long totalData;
	private int totalPages;
	
	public ResponsePaginationDto(Page<T> page) {
		this.status = 200;
		this.message = "success";
		this.data = page.getContent();
		this.totalData = page.getTotalElements();
		this.totalPages = page.getTotalPages();
	}
}
