package com.duetto.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {"userId", "roomId"})
	    }
	)
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String userId;
	private String roomId;
	
	public Users() {
		super();
	}
	
	public Users(String userId, String roomId) {
		super();
		this.userId = userId;
		this.roomId = roomId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
