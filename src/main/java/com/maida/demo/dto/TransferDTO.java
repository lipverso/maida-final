package com.maida.demo.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maida.demo.model.User;

public class TransferDTO {
	@JsonProperty("source_account_number")
	private String sourceAccountNumber;
	
	@JsonProperty("destination_account_number")
	private String desinationAccountNumber;
	
	private BigDecimal amount;
	
	private User user;

	public void setUser_transfer(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public String getDesinationAccountNumber() {
		return desinationAccountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
}
