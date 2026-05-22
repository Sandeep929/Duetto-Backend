package com.duetto.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duetto.demo.entity.Song;
import com.duetto.demo.entity.SongPlaylist;
import com.duetto.demo.repository.SongPlaylistRepository;
import com.duetto.demo.repository.SongRepository;

@Service
public class SongPlaylistService {

	@Autowired
	SongPlaylistRepository songPlaylistRepo;
	@Autowired
	SongRepository songRepository;
	
	public SongPlaylist addSongsInPlaylist(SongPlaylist songInPlaylist) {
		try {
			Integer max =
					songPlaylistRepo.findMaxSequence(
							songInPlaylist.getPlaylistId()
			        );

			songInPlaylist.setSequence(
			        max == null ? 1 : max + 1
			    );
			SongPlaylist song = songPlaylistRepo.save(songInPlaylist);
			return song;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean removeSongFromPlaylist(SongPlaylist song) {
		try {
			songPlaylistRepo.deleteByPlaylistIdAndUserIdAndSongId(song.getPlaylistId(), song.getUserId(), song.getSongId());
			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Song> getSongs(String userId, String playlistId){
		try {
			List<Song> songs = songPlaylistRepo.findPlaylistSongs(userId, playlistId);
			
			return songs;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
