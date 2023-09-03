package com.syahid.test.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.syahid.test.business.dto.ResponseDto;

@RestControllerAdvice
public class RestControllerAdviceHandler {
	
	@ExceptionHandler(value = {AnException.class})
	public ResponseEntity<ResponseDto<Object>> resourceAnException(AnException ex) {
		return new ResponseEntity<ResponseDto<Object>>(ResponseDto.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {RuntimeException.class})
	public ResponseEntity<ResponseDto<Object>> resourceRuntimeException(RuntimeException ex) {
		ex.printStackTrace();
		return new ResponseEntity<ResponseDto<Object>>(ResponseDto.internalError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
