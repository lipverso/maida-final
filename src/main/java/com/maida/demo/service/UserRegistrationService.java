package com.maida.demo.service;

import org.springframework.stereotype.Service;

import com.maida.demo.exception.UserAlreadyRegisteredException;
import com.maida.demo.model.User;
import com.maida.demo.repository.UserRepository;

@Service
public class UserRegistrationService {
	
	private UserRepository userRepository;
	
	private TokenService tokenService;
	
	public UserRegistrationService(UserRepository userRepository, TokenService tokenService) {
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}

	public User registrate(User user) {
		var savedUser = userRepository.findByEmail(user.getEmail()).orElse(null);
		if (savedUser != null) {
			throw new UserAlreadyRegisteredException("Este usuário já está registrado");
		}
		user.setToken(tokenService.generateToken(user));
		userRepository.save(user);
		return user;
		
	}

}
