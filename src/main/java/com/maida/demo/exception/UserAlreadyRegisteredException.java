package com.maida.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Already Registered")
public class UserAlreadyRegisteredException extends RuntimeException {
	public UserAlreadyRegisteredException(String msg) {
		super(msg);
	}
}
