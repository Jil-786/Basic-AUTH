package com.spring.Security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.Security.entity.UserEntity;
import com.spring.Security.service.SIgnUpService;

//import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Controller {
	
	@Autowired
	private SIgnUpService service;
	
	@GetMapping("/session")
	public String hello(HttpServletRequest http) {
		//http.get
		return "hi this is Jilani the session id is "+http.getRequestedSessionId();
	}
	
	@GetMapping("/test")
	//@ResponseBody
	public String hi() {
		return "hi babu";
	}
	
	
	@GetMapping("/csrf")
	public CsrfToken getToken(HttpServletRequest http) {
		return (CsrfToken) http.getAttribute("_csrf");
		
	}
	@PostMapping("/signup")
	public Map<String, Object> adduser(@RequestBody UserEntity user) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        if (user == null) {
	            response.put("message", "Invalid user data");
	            return response;
	        }

	        service.addUser(user);

	        response.put("message", "Signup successful");
	        response.put("email", user.getUsername()); // or user.getEmail()
	        response.put("token", 1); // You can generate JWT later

	        return response;

	    } catch (Exception e) {
	        response.put("message", e.getMessage());
	        return response;
	    }
	}

	@PostMapping("/login")
	public String login(@RequestBody UserEntity user) {
		 return service.Verify(user);
	}

}
