package com.nagarro.accounts.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

	private int id;
	private String type;
	private String number;
	private List<Transactions> transactions;
	
	public Account(int id, String type, String number) {
		super();
		this.id = id;
		this.type = type;
		this.number = number;
	}
}
