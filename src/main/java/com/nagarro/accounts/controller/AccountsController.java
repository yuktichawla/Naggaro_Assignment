package com.nagarro.accounts.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.accounts.aspect.CheckRole;
import com.nagarro.accounts.exception.ApplicationException;
import com.nagarro.accounts.filter.JwtTokenUtil;
import com.nagarro.accounts.model.Account;
import com.nagarro.accounts.model.TransactionSearchModel;
import com.nagarro.accounts.service.AccountStatementService;

@RestController
@RequestMapping("/account")
public class AccountsController {

	@Autowired
	private AccountStatementService service;
	@Autowired
	JwtTokenUtil jwttokenUtil;
	
	@GetMapping(value = "{id}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	@CheckRole
	public ResponseEntity<?> getaccountStatement(@PathVariable int id,
			@RequestParam(required = false, value = "toDate") String toDate,
			@RequestParam(required = false, value = "fromDate") String fromDate,
			@RequestParam(required = false, value = "fromAmout") BigDecimal fromAmount,
			@RequestParam(required = false, value = "toAmount") BigDecimal toAmount, HttpServletRequest request)
			throws ApplicationException, NoSuchAlgorithmException {
		String userName = jwttokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7)).toString();
		return new ResponseEntity<Account>(
				service.getAccountTransactions(new TransactionSearchModel(id, fromDate, toDate, fromAmount, toAmount), userName),
				HttpStatus.OK);

	}
}
