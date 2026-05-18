package com.duetto.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.duetto.demo.entity.Users;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<Users, String> {
	
	boolean existsByroomIdAndUserId(String roomId, String userId);
	
	@Transactional
	void deleteByRoomIdAndUserId(String roomId, String userId);
	
	@Query("select u.userId from Users u where roomId = :roomId")
	Set<String> getUserIdByRoomId(String roomId, String UserId); 
}
