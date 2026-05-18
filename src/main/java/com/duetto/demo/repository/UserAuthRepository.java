package com.duetto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duetto.demo.entity.User;

public interface UserAuthRepository extends JpaRepository<User, String> {
	boolean existsByUserIdAndPass(
			String userId, String pass);
	
}
