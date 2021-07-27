package com.maida.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "O token enviado está expirado")
public class ExpiredTokenException extends RuntimeException {

}
