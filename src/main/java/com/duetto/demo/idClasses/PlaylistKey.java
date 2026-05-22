package com.duetto.demo.idClasses;

import java.io.Serializable;
import java.util.Objects;

public class PlaylistKey implements Serializable {
	private String playlistId;
	private String userId;
	private Long songId;
	
	public PlaylistKey() {
		
	}
	
	public PlaylistKey(String playlistId, String userId, Long songId) {
		this.playlistId = playlistId;
		this.userId = userId;
		this.songId = songId;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		PlaylistKey plk = (PlaylistKey) obj;
		return playlistId.equals(plk.playlistId) && 
				userId.equals(plk.userId) &&
				songId.equals(plk.songId);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(playlistId, userId, songId);
	}
}
