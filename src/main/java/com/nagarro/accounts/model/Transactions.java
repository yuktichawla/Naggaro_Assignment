package com.nagarro.accounts.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

	private int id;
	private int accountId;
	private String date;
	private BigDecimal amount;
}
