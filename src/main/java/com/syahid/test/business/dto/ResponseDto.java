package com.syahid.test.business.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseDto<T> {

	private int status;
	private String message;
	private T data;
	
	public static <T> ResponseDto<T> success(T data) {
		ResponseDto<T> response = new ResponseDto<>();
		response.status = 200;
		response.message = "success";
		response.data = data;
		return response;
	}

	public static ResponseDto<Object> success() {
		ResponseDto<Object> response = new ResponseDto<>();
		response.status = 200;
		response.message = "success";
		response.data = null;
		return response;
	}
	
	public static ResponseDto<Object> error(String message) {
		ResponseDto<Object> response = new ResponseDto<>();
		response.status = 400;
		response.message = message;
		response.data = null;
		return response;
	}
	
	public static ResponseDto<Object> internalError(String message) {
		ResponseDto<Object> response = new ResponseDto<>();
		response.status = 500;
		response.message = message;
		response.data = null;
		return response;
	}
}
