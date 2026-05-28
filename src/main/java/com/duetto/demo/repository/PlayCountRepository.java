package com.duetto.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository

public class PlayCountRepository {

	@Autowired
	JdbcTemplate jdbc;
	
	public void bulkIncrementPlayCounts(Map<Long, Long> counts) {
	    String sql = "UPDATE song SET plays = plays + ? WHERE id = ?";

	    List<Object[]> batchArgs = counts.entrySet().stream()
	        .map(e -> new Object[]{e.getValue(), e.getKey()})
	        .toList();

	    jdbc.batchUpdate(sql, batchArgs);
	}
}
