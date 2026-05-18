package com.duetto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duetto.demo.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>{

}
