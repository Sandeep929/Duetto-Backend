package com.duetto.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duetto.demo.entity.Song;
import com.duetto.demo.repository.SongRepository;

@Service
public class SongService {
	
	@Autowired
	SongRepository songRepository;
	
	public List<Song> getAllSongs(){
		return songRepository.findAll();
	}
}
