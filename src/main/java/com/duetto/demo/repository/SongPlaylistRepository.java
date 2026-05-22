package com.duetto.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duetto.demo.entity.Song;
import com.duetto.demo.entity.SongPlaylist;
import com.duetto.demo.idClasses.PlaylistKey;

import jakarta.transaction.Transactional;

@Repository
public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, PlaylistKey> {
	
	@Transactional
	public void deleteByPlaylistIdAndUserIdAndSongId(String playListId, String userId, Long songId);
	
	@Query("""
			SELECT s
			FROM Song s
			JOIN SongPlaylist sp
			ON s.id = sp.songId
			WHERE sp.userId=:userId
			AND sp.playlistId=:playlistId
			ORDER BY sp.sequence ASC
			""")
			List<Song> findPlaylistSongs(
			        @Param("userId") String userId,
			        @Param("playlistId") String playlistId
			);
	
	@Query("""
			SELECT MAX(sp.sequence)
			FROM SongPlaylist sp
			WHERE sp.playlistId=:playlistId
			""")
			public Integer findMaxSequence(
			    @Param("playlistId")
			    String playlistId
			);
}
