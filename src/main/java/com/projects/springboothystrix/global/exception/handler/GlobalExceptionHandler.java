package com.projects.springboothystrix.global.exception.handler;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.projects.springboothystrix.exception.StudentException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlerException(Exception e){
		return new ResponseEntity<>(new StudentException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
