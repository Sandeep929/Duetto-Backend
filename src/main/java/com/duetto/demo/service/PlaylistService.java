package com.duetto.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duetto.demo.entity.Playlist;
import com.duetto.demo.repository.PlaylistRepository;

@Service
public class PlaylistService {
	
	@Autowired
	PlaylistRepository playlistRepo;
	
	public Playlist createPlaylist(Playlist playlist) {
		return playlistRepo.save(playlist);
	}
	
	public boolean deletePlaylist(String playlistId) {
		try {
			playlistRepo.deleteById(playlistId);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Playlist> getAllPlaylist(String userId){
		try {
			List<Playlist> list =  playlistRepo.findAllByUserId(userId);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
