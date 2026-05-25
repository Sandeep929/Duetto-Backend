package com.duetto.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.duetto.demo.entity.Song;
import com.duetto.demo.service.CloudinaryService;
import com.duetto.demo.service.SongService;

@RestController
@RequestMapping("/songs")
public class SongController {
	private final CloudinaryService cloudinaryService;
	private final SongService songService;
	public SongController(CloudinaryService cloudinaryService, SongService songService) {
		this.cloudinaryService = cloudinaryService;
		this.songService = songService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<List<Map>> uploadSong(@RequestParam("song") List<MultipartFile> songs) {
		System.out.println("request reached");
		List<Map> res = cloudinaryService.uploadSongs(songs);
//		System.out.println(res);
		if(res != null) {
			List<Song> saved_songs =  cloudinaryService.getAllSongById(res);
			if(saved_songs != null) return ResponseEntity.ok(res);
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	
	@GetMapping("/getAllSongs")
	public List<Song> getSongs() {
	    return cloudinaryService.getAllSongs();
	}
	
	@GetMapping("/getLocalSongs")
	public List<Song> getLocalSongs(){
		return songService.getAllSongs();
	}
	
	@DeleteMapping("/removeSongs")
	public Map removeSong(@RequestBody List<Song> songs){
		return cloudinaryService.removeSongs(songs);
	}
}
