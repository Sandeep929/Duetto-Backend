package com.duetto.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duetto.demo.entity.User;
import com.duetto.demo.service.UserAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserAuthService userAuthService;
	
	@GetMapping("/login")
	public ResponseEntity<String> checkUser(@RequestParam String userId,
			@RequestParam String pass) {
		if(userAuthService.checkUser(userId, pass)) {
			return ResponseEntity.ok(userId);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/register")
	public User createUser(@RequestBody User user) {
		return userAuthService.createUser(user);
	}
}
