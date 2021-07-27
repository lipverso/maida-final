package com.maida.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maida.demo.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public Optional<Account> findByNumber(String number);

}
