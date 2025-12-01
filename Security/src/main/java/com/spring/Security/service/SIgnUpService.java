package com.spring.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.Security.entity.UserEntity;
import com.spring.Security.repo.UserRepo;

@Service
public class SIgnUpService {
	
	@Autowired
	private UserRepo repo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void addUser(UserEntity user) throws Exception {
		UserEntity existsuser = repo.findByUsername(user.getUsername());
		if(existsuser!=null) {
			throw new Exception("User Exists");
		}
		   String encodedPassword = passwordEncoder.encode(user.getPassword());
		    user.setPassword(encodedPassword);
		repo.save(user);
		
		
	}

}
