package com.nagarro.accounts.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSearchModel {

	private int id;
	private String fromDate;
	private String toDate;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
}
