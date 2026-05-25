package com.duetto.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duetto.demo.entity.User;
import com.duetto.demo.repository.UserAuthRepository;
import com.duetto.demo.service.UserAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthRepository userAuthRepository;
	
	@Autowired
	UserAuthService userAuthService;

    AuthController(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }
	
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
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> list = userAuthService.getAllUsers();
			if(list != null) return ResponseEntity.ok(list);
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}		
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity deleteUser(@RequestParam List<User> users){
		try {
			if(userAuthService.removeUsers(users)) return ResponseEntity.noContent().build();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
}
