package com.duetto.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.duetto.demo.dto.LiveUsers;
import com.duetto.demo.dto.Song;
import com.duetto.demo.dto.SyncMessage;
import com.duetto.demo.repository.RoomRepository;
import com.duetto.demo.service.RoomService;

@Controller
public class SyncController {
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	RoomService roomService;
    
	@MessageMapping("/sync/{roomId}")
	@SendTo("/topic/room/{roomId}/sync")
	public SyncMessage handleSync(@DestinationVariable String roomId, 
			SyncMessage message) {
		
		if(!roomRepository.existsById(roomId)) {
			throw new RuntimeException("Room Not Found");
		}
		if(!roomService.updateRoomState(roomId, message)) {
			System.out.println("Cache cant be upadate");
			return null;
		}
		System.out.println("message from: "+message.getSender()+"Received: "+message.getAction()+ " for room: "+roomId);
		return message;
	}
	
	@MessageMapping("/userCount/{roomId}")
	@SendTo("/topic/room/{roomId}/users")
	public LiveUsers userCount(@DestinationVariable String roomId) {
		
		if(!roomRepository.existsById(roomId)) {
			throw new RuntimeException("Room Not Found");
		}
		
		LiveUsers users = new LiveUsers();
		users.setRoomId(roomId);
		users.setUserCount(roomService.currentUsers(roomId));
		users.setUsers(roomService.currentUsersNames(roomId));
		System.out.println(users.getUserCount());
		return users;
	}
	@MessageMapping("/loadSongs/{roomId}")
	@SendTo("/topic/room/{roomId}/songs")
	public List<Song> loadSongs(@DestinationVariable String roomId, @Payload List<Song> loadedSongs) {
		
		if(!roomRepository.existsById(roomId)) {
			throw new RuntimeException("Room Not Found");
		}
		
		System.out.println("Loaded Songs: "+loadedSongs);
		return loadedSongs;
	}
}
