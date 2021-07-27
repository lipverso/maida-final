package com.maida.demo.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maida.demo.exception.UserNotFoundException;
import com.maida.demo.model.User;
import com.maida.demo.repository.UserRepository;
import com.maida.demo.service.UserRegistrationService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRegistrationService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<User> registrate (@Valid @RequestBody User user, HttpServletResponse response){
		var newUser = userService.registrate(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(newUser.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(newUser);
	}
	
	@GetMapping("/{id}")
	public User listar(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void DeleteMapping(@PathVariable Long id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping
	public List<User> listar() {
		return userRepository.findAll();
	}
}
