package com.duetto.demo.dto;

import java.util.Set;

public class LiveUsers {
	
	private Set<String> users;
	private int userCount;
	private String roomId;
	
	public Set<String> getUsers() {
		return users;
	}
	public void setUsers(Set<String> users) {
		this.users = users;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
