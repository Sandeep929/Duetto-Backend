package com.duetto.demo.service;
import java.util.List;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.duetto.demo.entity.Song;
import com.duetto.demo.repository.PlayCountRepository;
import com.duetto.demo.repository.SongRepository;

@Service
public class SongService {
	
	private final SongRepository songRepository;
	private final PlaysIncrement pi;
	private final PlayCountRepository pcr;

	public SongService(SongRepository songRepository, PlaysIncrement pi, PlayCountRepository pcr) {
		super();
		this.songRepository = songRepository;
		this.pi = pi;
		this.pcr = pcr;
	}

	public List<Song> getAllSongs(){
		return songRepository.findAll();
	}
	
	@Scheduled(fixedRate = 30000)
	public void updatePlays() {
		System.out.println("he he he he he");
		Map<Long, Long> counts = pi.drainBuffer();
		if (counts.isEmpty()) return;

        // One bulk query — not N queries
        pcr.bulkIncrementPlayCounts(counts);
	}
	
	public List<Song> getTop5(){
		return songRepository.findTop5ByOrderByPlaysDesc();
	}
}
