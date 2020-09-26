package com.nagarro.accounts.service.impl;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nagarro.accounts.entity.AccountEntity;
import com.nagarro.accounts.entity.TransactionEntity;
import com.nagarro.accounts.exception.ApplicationException;
import com.nagarro.accounts.model.Account;
import com.nagarro.accounts.model.TransactionSearchModel;
import com.nagarro.accounts.model.Transactions;
import com.nagarro.accounts.repository.AccountRepository;
import com.nagarro.accounts.service.AccountStatementService;

@Service
public class AccountStatementServiceImpl implements AccountStatementService {

	@Autowired
	private AccountRepository rep;

	@Override
	public Account getAccountTransactions(TransactionSearchModel request, String userName)
			throws ApplicationException, NoSuchAlgorithmException {
		AccountEntity entity = rep.findOne(request.getId());
		if (entity != null) {
			if(!entity.getUser().getUserName().equalsIgnoreCase(userName)) {
				throw new ApplicationException("UNAUTHORIZED_ACCOUNT", "You are not authorized for this account");
			}
			Account response = new Account(entity.getId(), entity.getAccount_type(),
					getHashedAccountId(entity.getAccount_number()));
			if (entity.getTransactions() != null && !entity.getTransactions().isEmpty()) {
				List<Transactions> transactions = entity.getTransactions().stream()
						.filter(trans -> {
							return filterTransaction(request, trans);
						}).map(t -> {
							return new Transactions(t.getId(), t.getAccount().getId(), t.getDatefield(),
									new BigDecimal(t.getAmount()));
						}).filter(Objects::nonNull).collect(Collectors.toList());
				if (transactions != null && transactions.size() > 0) {
					response.setTransactions(transactions);
				}
			}
			return response;
		} else {
			throw new ApplicationException("ACCOUNT_ID_NOT_FOUND", "Account id not found.",
					HttpStatus.NOT_FOUND.value());
		}
	}

	private String getHashedAccountId(String hashText) throws NoSuchAlgorithmException {
		if (StringUtils.isEmpty(hashText))
			return hashText;
		MessageDigest digest;
		byte[] byteArray = null;

		digest = MessageDigest.getInstance("SHA-256");
		digest.update(hashText.getBytes());
		byteArray = digest.digest();
		StringBuilder response = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			response.append(Integer.toString((byteArray[i] & 0xff) + 0x100, 16).substring(1));
		}
		return response.toString();
	}

	private boolean filterTransaction(TransactionSearchModel request, TransactionEntity trans) throws ApplicationException {
		BigDecimal amount = new BigDecimal(trans.getAmount());
		if(request.getToAmount() != null && amount.compareTo(request.getToAmount())>1
				|| (request.getFromAmount() != null && amount.compareTo(request.getFromAmount())<1)
				|| (!validateToAndFromDate(request.getFromDate(),request.getToDate(),trans.getDatefield()))
				) {
			return false;
		}
		/*
		 * if(fromDate != null && toDate != null) {
		 * 
		 * } else {
		 * 
		 * Calendar calendar = Calendar.getInstance(); calendar.add(Calendar.MONTH, -3);
		 * BigInteger fromTimeInMillis = new BigInteger("" +
		 * calendar.getTimeInMillis()); BigInteger toTimeInMillis = new BigInteger("" +
		 * System.currentTimeMillis()); SimpleDateFormat sdf = new
		 * SimpleDateFormat("dd.MM.yyyy"); fromDate =
		 * 
		 * }
		 */
		return true;
	}

	private boolean validateToAndFromDate(String fromDate, String toDate, String datefield) throws ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		try {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("IST")); 
			calendar.add(Calendar.MONTH, -3);
			
			Date transactionDate = sdf.parse(datefield);
			Date fromDateObj = null;
			Date toDateObj = null;
			if(!StringUtils.isEmpty(fromDate)) {
				fromDateObj = sdf.parse(fromDate);
			} else {
				fromDateObj = new Date(calendar.getTimeInMillis());
			} 
			if(!StringUtils.isEmpty(toDate)) {
				toDateObj = sdf.parse(toDate);
			} else {
				toDateObj = new Date(System.currentTimeMillis());
			}
			 
			if((toDateObj.before(fromDateObj)) || (transactionDate.after(toDateObj)) || (transactionDate.before(fromDateObj)))
				return false;
		} catch (ParseException e) {
			throw new ApplicationException("INVALID_DATE", "Invalid to or from date");
		}
		return true;
	}

}
