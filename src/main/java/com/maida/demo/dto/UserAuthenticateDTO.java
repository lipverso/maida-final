package com.maida.demo.dto;

import com.maida.demo.model.User;

public class UserAuthenticateDTO {
	
	private String type;
	private String email;
	private String name;
	private String token;
	
	public UserAuthenticateDTO (String email, String name, String token, String type) {
		this.email = email;
		this.name = name;
		this.token = token;
		this.type = type;
	}
	
	public UserAuthenticateDTO() {
		
	}
	
	public static UserAuthenticateDTO toDTO (User user, String type) {
		return new UserAuthenticateDTO(user.getEmail(), user.getName(), user.getToken(), type);
	}

	public String getType() {
		return type;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}
	

}
