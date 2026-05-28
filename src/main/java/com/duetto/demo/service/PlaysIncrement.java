package com.duetto.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

import org.springframework.stereotype.Service;

import com.duetto.demo.entity.Song;

@Service

public class PlaysIncrement {
	
	
	ConcurrentHashMap<Long, LongAdder> obj = new ConcurrentHashMap<Long, LongAdder>();
	
	public boolean writePlays(Map<Long, Long> plays) {
	    try {
	        plays.forEach((songId, count) -> {
	        	obj.computeIfAbsent(songId, id -> new LongAdder()).add(count);
	        	System.out.println(songId+" <- id aur count -> "+obj.get(songId));
	        }
	        );
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public Map<Long, Long> drainBuffer() {
        Map<Long, Long> snapshot = new HashMap<>();
        obj.forEach((songId, adder) -> {
            snapshot.put(songId, adder.sumThenReset());
        });
        return snapshot;
    }
}
