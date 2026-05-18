package com.duetto.demo.dto;

public class SyncMessage {

	private String action;
	private long time;
	private String sender;
	private boolean playing;
	private String trackId;
	private double currentTime;
	private String roomId;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public boolean isPlaying() {
		return playing;
	}
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	public String getTrackId() {
		return trackId;
	}
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	public double getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(double currentTime) {
		this.currentTime = currentTime;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
