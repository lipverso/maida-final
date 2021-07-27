package com.maida.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Já existe uma conta com o número informado")
public class AccountNumberAlreadyExistentException extends RuntimeException {

}
