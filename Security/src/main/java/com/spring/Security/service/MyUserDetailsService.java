package com.spring.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.Security.entity.UserEntity;
import com.spring.Security.entity.UserPrincipal;
import com.spring.Security.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity user = userRepo.findByUsername(username);
		if(user==null) {
			System.out.println("User not found in DB");
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPrincipal(user);
	}

}
