package com.nagarro.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.accounts.entity.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer>{

}
