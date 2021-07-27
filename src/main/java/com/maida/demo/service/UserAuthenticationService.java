package com.maida.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maida.demo.exception.ExistingEmailException;
import com.maida.demo.exception.InvalidLoginException;
import com.maida.demo.model.Login;
import com.maida.demo.model.User;
import com.maida.demo.repository.UserRepository;

@Service
public class UserAuthenticationService {
	
	private UserRepository userRepository;
	
	private TokenService tokenService;
	
	//Constructor injection
	@Autowired
	public UserAuthenticationService (UserRepository userRepository, TokenService tokenService) {
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}

	public User authenticate(Login data, String authToken) {
		User user = userRepository.findByEmail(data.getEmail()).orElseThrow(ExistingEmailException::new);
		boolean isValidPassword = data.getPassword().equals(user.getPassword());
		
		if (isValidPassword && !authToken.isEmpty() && TokenService.validate(authToken)) {
			 return user;
		} else {
			throw new InvalidLoginException();
		}
	}

}
