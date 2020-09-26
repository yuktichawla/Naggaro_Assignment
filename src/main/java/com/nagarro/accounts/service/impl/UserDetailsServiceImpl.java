package com.nagarro.accounts.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nagarro.accounts.entity.UserEntity;
import com.nagarro.accounts.repository.LoginRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{

	@Autowired
	private LoginRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = repository.findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid user name or password");
		}

	    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	    grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

	    return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), grantedAuthorities);
}

}
