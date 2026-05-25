package com.duetto.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.duetto.demo.entity.User;
import com.duetto.demo.repository.UserAuthRepository;

@Service
public class UserAuthService {

	@Autowired
	UserAuthRepository userAuthRepo;
	public User createUser(User user) {
		return userAuthRepo.save(user);
	}
	
	public boolean checkUser(String userId, String pass) {
		return userAuthRepo.existsByUserIdAndPass(userId, pass);
	}
	
	public List<User> getAllUsers(){
		return userAuthRepo.findAll();
	}
	
	public boolean removeUsers(List<User> list){
		try {
			userAuthRepo.deleteAll(list);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
