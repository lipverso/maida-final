package com.maida.demo.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.maida.demo.exception.ExpiredTokenException;
import com.maida.demo.exception.InvalidTokenException;
import com.maida.demo.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	private static final Long expirationTime = 18000000L;
	private static final String key = "Maida Security";
	
	public String generateToken(User user) {
		return Jwts.builder()
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setSubject("Teste JWT Api")
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
	}

	public static Claims decodeToken(String authToken) {
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(authToken)
				.getBody();
	}
	
	public static boolean validate(String authToken) {
		try {
			String validTokenString = authToken.replace("Bearer","");
			Claims claims = decodeToken(authToken);
			System.out.println(claims.getIssuer());
			System.out.println(claims.getIssuedAt());
			
			if(claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
				throw new ExpiredTokenException();
			}
			return true;
		} catch (ExpiredTokenException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidTokenException();
		}
		return false;
	}

}
