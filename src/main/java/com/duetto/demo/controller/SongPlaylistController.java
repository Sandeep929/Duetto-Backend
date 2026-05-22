package com.duetto.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duetto.demo.entity.Song;
import com.duetto.demo.entity.SongPlaylist;
import com.duetto.demo.service.SongPlaylistService;

@RestController
@RequestMapping("/playlist-songs")
public class SongPlaylistController {
	
	@Autowired
	SongPlaylistService songPlaylistService;
	
	@PostMapping("/add-song")
	public ResponseEntity<SongPlaylist> addSongToPlaylist(@RequestBody SongPlaylist song) {
		return ResponseEntity.ofNullable(songPlaylistService.addSongsInPlaylist(song));				
	}
	
	@PostMapping("/remove-song")
	public ResponseEntity removeSongFromPlaylist(@RequestBody SongPlaylist song){
		boolean b = songPlaylistService.removeSongFromPlaylist(song);
		if(b) return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/getAllSongs")
	public List<Song> getAllSongFromPlaylist(@RequestParam String userId,@RequestParam String playlistId) {
		List<Song> songList =  songPlaylistService.getSongs(userId, playlistId);
		if(songList != null) return songList;
		return null;
	}
}
