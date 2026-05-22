package com.duetto.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duetto.demo.entity.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {

	List<Playlist> findAllByUserId(String userId);
	
}
