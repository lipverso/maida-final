package com.maida.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.maida.demo.dto.UserAuthenticateDTO;
import com.maida.demo.model.Login;
import com.maida.demo.model.User;
import com.maida.demo.service.UserAuthenticationService;

@RestController
public class UserAuthenticationController {
	
	private UserAuthenticationService userAuthenticationService;
	
	@Autowired
	public UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
		this.userAuthenticationService = userAuthenticationService;
	}
	
	@PostMapping("/auth")
	public ResponseEntity<UserAuthenticateDTO> authenticate(@RequestBody Login data, @RequestHeader String Authorization){
		User user = userAuthenticationService.authenticate(data, Authorization);
		return new ResponseEntity<UserAuthenticateDTO>(UserAuthenticateDTO.toDTO(user, "Bearer"), HttpStatus.ACCEPTED);
	}
	
}
