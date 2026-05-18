package com.duetto.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duetto.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	
}
