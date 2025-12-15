package com.spring.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
	
	@Autowired
	private AuthenticationManager authManger;
	
	@Autowired
	private JWTService jwtService;
	
	public void addUser(UserEntity user) throws Exception {
		UserEntity existsuser = repo.findByUsername(user.getUsername());
		if(existsuser!=null) {
			throw new Exception("User Exists");
		}
		   String encodedPassword = passwordEncoder.encode(user.getPassword());
		    user.setPassword(encodedPassword);
		repo.save(user);
		
	}
	
	public String Verify(UserEntity user) {
		Authentication auth = authManger.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		
		if(auth.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		}
		return "Failure";
	}

}
