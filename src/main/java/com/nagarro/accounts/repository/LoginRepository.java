package com.nagarro.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.accounts.entity.UserEntity;

@Repository
public interface LoginRepository extends CrudRepository<UserEntity, Integer>{

	UserEntity findByUserNameAndUserPassword(String userName, String password);

	UserEntity findByUserName(String username);

}
