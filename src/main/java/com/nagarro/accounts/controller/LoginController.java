package com.nagarro.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.accounts.exception.ApplicationException;
import com.nagarro.accounts.filter.JwtTokenUtil;
import com.nagarro.accounts.model.LoginRequest;
import com.nagarro.accounts.model.LoginResponse;
import com.nagarro.accounts.service.impl.UserDetailsServiceImpl;

@RestController
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getaccountStatement(@RequestBody LoginRequest request) throws Exception {
		authenticate(request.getUserName(), request.getPassword());
		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(request.getUserName());
		} catch (Exception e) {
			throw new ApplicationException("INVALID_CREDENTIALS", "Invalid UserName or Password", HttpStatus.UNAUTHORIZED.value());
		}
		String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new LoginResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			throw new ApplicationException("INVALID_CREDENTIALS", "Invalid UserName or Password", HttpStatus.UNAUTHORIZED.value());
		}
	}
}
