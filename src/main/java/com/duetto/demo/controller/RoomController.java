package com.duetto.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duetto.demo.dto.SyncMessage;
import com.duetto.demo.entity.Room;
import com.duetto.demo.entity.Users;
import com.duetto.demo.service.RoomService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/room")
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createRoom(@RequestParam String hostId) {
		//TODO: process POST request
		String roomId = roomService.createRoom(hostId);
		return ResponseEntity.ok(roomId);
	}
	
	@PostMapping("/join")
	public ResponseEntity<String> joinRoom(@RequestParam String roomId, 
										@RequestParam String userId)
	{
		String res = roomService.joinRoom(roomId, userId);
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/userCount")
	public ResponseEntity<Integer> getUserCount(@RequestParam String roomId){
		Integer count = roomService.currentUsers(roomId);
		return ResponseEntity.ok(count);
		}

	@PostMapping("/removeUser")
	public Users deleteUser(@RequestParam String roomId, @RequestParam String userId) {
		return roomService.removeUser(userId, roomId);
	}
	
	@DeleteMapping("/deleteRoom")
	public ResponseEntity deleteRoom(@RequestBody Room room){
		try {
			if(roomService.deleteRoom(room)) return ResponseEntity.noContent().build();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/getAllRooms")
	public ResponseEntity getAllRooms(){
		try {
			List<Room> list = roomService.getAllRooms();
			if(list != null) return ResponseEntity.ok(list);
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/checkRoomState")
	public SyncMessage checkRoomStateOnJR(@RequestParam String roomId) {
		return roomService.getRoomState(roomId);
	}
}
