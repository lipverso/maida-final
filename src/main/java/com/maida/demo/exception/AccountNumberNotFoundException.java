package com.maida.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Conta de origem não encontrada para o usuário informado")
public class AccountNumberNotFoundException extends RuntimeException {

}
