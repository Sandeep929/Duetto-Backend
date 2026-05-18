package com.duetto.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<Map> uploadSong(List<MultipartFile> songs) {
		return cloudinaryService.uploadSongs(songs);
	}
	
	@GetMapping("/getAllSongs")
	public List<Song> getSongs() {
	    return cloudinaryService.getAllSongs();
	}
	
	@GetMapping("/getLocalSongs")
	public List<Song> getLocalSongs(){
		return songService.getAllSongs();
	}
}
