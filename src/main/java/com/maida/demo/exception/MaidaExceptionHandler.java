package com.maida.demo.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.*;

@ControllerAdvice
public class MaidaExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> errors = generateErrorList(ex.getBindingResult());
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Error> generateErrorList(BindingResult bindingResult) {
		List<Error> errors = new ArrayList<>();
		for(FieldError fieldError: bindingResult.getFieldErrors()) {
			String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String developerMessage = fieldError.toString();
			errors.add(new Error(userMessage, developerMessage));
		}
		return errors;
	}

	public static class Error {
		private String userMessage;
		private String developerMessage;
		
		public Error(String userMessage, String developerMessage){
			this.userMessage = userMessage;
			this.developerMessage = developerMessage;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public String getDeveloperMessage() {
			return developerMessage;
		}
		
	}

}
