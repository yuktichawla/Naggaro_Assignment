package com.nagarro.accounts.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="account")
public class AccountEntity {

	@Id
	private int id;
	private String account_type;
	private String account_number;
	@OneToMany(mappedBy = "account",fetch=FetchType.EAGER)
	private List<TransactionEntity> transactions;
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private UserEntity user;
}
