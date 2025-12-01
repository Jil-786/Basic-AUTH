package com.spring.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.Security.entity.UserEntity;
import com.spring.Security.service.SIgnUpService;

//import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Controller {
	
	@Autowired
	private SIgnUpService service;
	
	@GetMapping("/")
	public String hello(HttpServletRequest http) {
		//http.get
		return "hi this is Jilani the session id is "+http.getRequestedSessionId();
	}
	@GetMapping("/csrf")
	public CsrfToken getToken(HttpServletRequest http) {
		return (CsrfToken) http.getAttribute("_csrf");
		
	}
	@PostMapping("/signup")
	public String adduser(@RequestBody UserEntity user) throws Exception {
		if(user==null) {
			return "Correctly add user";
		}
		service.addUser(user);
		return "Success";
	}

}
