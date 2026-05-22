package com.duetto.demo.entity;

import com.duetto.demo.idClasses.PlaylistKey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PlaylistKey.class)
public class SongPlaylist {
	
	@Id
	private String playlistId;
	@Id
	private String userId;
	@Id
	private Long songId;
	
	private Integer sequence;

	public SongPlaylist() {
		super();
	}

	public SongPlaylist(String playlistId, String userId, Long songId, int sequence) {
		super();
		this.playlistId = playlistId;
		this.userId = userId;
		this.songId = songId;
		this.sequence = sequence;
	}

	public String getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
}
