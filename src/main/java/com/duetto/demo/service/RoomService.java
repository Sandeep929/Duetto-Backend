package com.duetto.demo.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duetto.demo.entity.Room;
import com.duetto.demo.entity.Users;
import com.duetto.demo.repository.RoomRepository;
import com.duetto.demo.repository.UserRepository;

@Service
public class RoomService {
	
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	UserRepository userRepository;
	
	private Map<String, Set<String>> roomUsers = new ConcurrentHashMap<String, Set<String>>();
	
	public String createRoom(String hostId) {
		String roomId = UUID.randomUUID().toString();
		
		Room room = new Room();
		room.setRoomId(roomId);
		room.setHostId(hostId);
		room.setCreatedAt(LocalDateTime.now());
		
		roomRepository.save(room);
		
		roomUsers.put(roomId, new HashSet<String>());
		System.out.println(roomUsers.containsKey(roomId));
		
		return roomId;
	}
	
	public String joinRoom(String roomId, String userId) {
		
		if(!roomRepository.existsById(roomId)) {
			throw new RuntimeException("Room does not exist. Create room first.");
		}
		
		
		if(!roomUsers.containsKey(roomId)) {
			if(roomRepository.existsById(roomId)) {
				roomUsers.put(roomId, userRepository.getUserIdByRoomId(roomId, userId));
				System.out.println(roomUsers.get(roomId));
			}
			else {
				throw new RuntimeException("Room is not initialized properly");
			}
		}
		
		Set<String> users = roomUsers.get(roomId);
		System.out.println("Users: "+users);
		if(userRepository.existsByroomIdAndUserId(roomId,userId)) {
			throw new RuntimeException("User already joined");
		}
		
		if(users.contains(userId)) {
			throw new RuntimeException("User is already in the Room");
		}
			
		users.add(userId);
		Users user = new Users();
		user.setRoomId(roomId);
		user.setUserId(userId);
		userRepository.save(user);
		System.out.println("Current Room Users: " + roomUsers);
		
		return "Joined Successfully";
	}
	
	public int currentUsers(String roomId) {
		Set<String> users = roomUsers.get(roomId);
		return (users != null? users.size(): 0);
	}
	
	public Set<String> currentUsersNames(String roomId){
		Set<String> list = roomUsers.get(roomId);
		return list;
	}
	
	public Users removeUser(String userId, String roomId) {
		Set<String> users = roomUsers.get(roomId);
		if(users != null && users.remove(userId)) {
			userRepository.deleteByRoomIdAndUserId(roomId, userId);;
			return new Users(userId, roomId);
		}
		else {
			return null;
		}
	}
}
