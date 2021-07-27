package com.maida.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maida.demo.dto.TransferDTO;
import com.maida.demo.exception.AccountNumberAlreadyExistentException;
import com.maida.demo.exception.AccountNumberNotFoundException;
import com.maida.demo.exception.DestinationAccountNotFoundException;
import com.maida.demo.exception.NotEnoughMoneyException;
import com.maida.demo.exception.SourceAccountNotFoundException;
import com.maida.demo.exception.UnauthorizedAccessException;
import com.maida.demo.model.Account;
import com.maida.demo.model.User;
import com.maida.demo.repository.AccountRepository;
import com.maida.demo.repository.UserRepository;

@Service
public class AccountService {

	// Field injection
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	public Account register(Account account, String token) {
		var user = findUserByToken(token);

		if (user != null) {
			account.setUser(user);
		}

		saveAccount(account, token, user);

		return account;
	}

	private void saveAccount(Account account, String token, User user) {
		var newAccount = isAlreadyRegisteredAccount(account, token, user);

		if (!newAccount) {
			accountRepository.save(account);
		} else {
			throw new AccountNumberAlreadyExistentException();
		}

		/*
		 * if (newAccount == null) { accountRepository.save(account); } else { throw new
		 * AccountNumberAlreadyExistentException(); }
		 */
	}

	public Account showBalance(Account account, String token) {
		TokenService.validate(token);
		var user = findUserByToken(token);

		for (int i = 0; i < user.getAccounts().size(); i++) {
			if (user.getAccounts().get(i).getNumber().equals(account.getNumber())) {
				return user.getAccounts().get(i);
			}
		}

		throw new AccountNumberNotFoundException();

	}

	public TransferDTO transfer(TransferDTO transferDTO, String authorization) {
		TokenService.validate(authorization);
		var user = findUserByToken(authorization);

		var source = transferDTO.getSourceAccountNumber();
		var destination = transferDTO.getDesinationAccountNumber();
		var amount = transferDTO.getAmount();
		
		boolean isvalidSourceAccount = false, isvalidDestinationAccount = false;

		for (int i = 0; i < user.getAccounts().size(); i++) {
			var userAccount = user.getAccounts().get(i);
			if (userAccount.getNumber().equals(source)) {
				userAccount.setBalance(userAccount.getBalance().subtract(amount));
				if (userAccount.getBalance().compareTo(new BigDecimal(0.0)) < 0) {
					throw new NotEnoughMoneyException();
				}
				isvalidSourceAccount = true;
			} else if (userAccount.getNumber().equals(destination)) {
				userAccount.setBalance(userAccount.getBalance().add(amount));
				isvalidDestinationAccount = true;
			}
		}

		if (!isvalidDestinationAccount) {
			throw new DestinationAccountNotFoundException();
		}
		if (!isvalidSourceAccount) {
			throw new SourceAccountNotFoundException();
		}

		transferDTO.setUser_transfer(user);
		
		userRepository.save(user);
		
		return transferDTO;
	}

	private User findUserByToken(String token) {
		var user = userRepository.findByToken(token).orElseThrow(UnauthorizedAccessException::new);
		return user;
	}

	private boolean isAlreadyRegisteredAccount(Account account, String token, User user) {
		for (int i = 0; i < user.getAccounts().size(); i++) {
			if (user.getAccounts().get(i).getNumber().equals(account.getNumber())) {
				return true;
			}
		}
		return false;
	}

	public List<Account> list(String authorization) {
		var user = findUserByToken(authorization);
		List<Account> accounts = new ArrayList<>();

		for (int i = 0; i < user.getAccounts().size(); i++) {
			accounts.add(user.getAccounts().get(i));
		}

		return accounts;
	}

}
