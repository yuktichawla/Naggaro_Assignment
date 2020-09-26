package com.nagarro.accounts.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="statement")
public class TransactionEntity {

	@Id
	private int id;
	@ManyToOne
	@JoinColumn(name="account_id",nullable=false)
	private AccountEntity account;
	private String datefield;
	private String amount;
}
