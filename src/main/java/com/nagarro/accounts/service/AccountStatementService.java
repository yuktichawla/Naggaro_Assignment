package com.nagarro.accounts.service;

import java.security.NoSuchAlgorithmException;

import com.nagarro.accounts.exception.ApplicationException;
import com.nagarro.accounts.model.Account;
import com.nagarro.accounts.model.TransactionSearchModel;

public interface AccountStatementService {

	Account getAccountTransactions(TransactionSearchModel request, String userName)
			throws ApplicationException, NoSuchAlgorithmException;

}
