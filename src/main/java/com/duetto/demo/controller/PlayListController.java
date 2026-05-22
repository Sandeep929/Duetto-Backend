package com.duetto.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duetto.demo.entity.Playlist;
import com.duetto.demo.service.PlaylistService;

@RestController
@RequestMapping("/playList")
public class PlayListController {
	
	@Autowired
	PlaylistService playlistService;
	
	@PostMapping("/createPlaylist")
	public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist){
//		System.out.println("Request Reached");
		Playlist listObj = playlistService.createPlaylist(playlist);
		if(listObj != null) return ResponseEntity.ok(listObj);
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/delete-playlist")
	public ResponseEntity deletePlaylist(@RequestParam String playlistId){
		boolean res = playlistService.deletePlaylist(playlistId);
		if(res) return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/get-playlists")
	public ResponseEntity<List<Playlist>> getAllPlaylistByUserId(@RequestParam String userId){
		List<Playlist> list = playlistService.getAllPlaylist(userId);
		if(list != null) return ResponseEntity.ok(list);
		return ResponseEntity.badRequest().build();
	}
}
