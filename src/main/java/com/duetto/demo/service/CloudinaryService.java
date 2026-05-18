package com.duetto.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.duetto.demo.entity.Song;
import com.duetto.demo.repository.SongRepository;

@Service
public class CloudinaryService {
	private final Cloudinary cloudinary;
	private final SongRepository songRepository;
//I used constructor injection here because of the dependency which has to be injected is marked
//	as private and final so when we have a condition like that then we use the constructor injection
	public CloudinaryService(Cloudinary cloudinary, SongRepository songRepository) {	
		this.cloudinary = cloudinary;
		this.songRepository = songRepository;
	}
	
	public Map uploadSong(MultipartFile song) {
		
		try {
			Map<String, Object> options = new HashMap<String, Object>();
			
			options.put("resource_type", "video");
			options.put("folder", "songs");
			
			return cloudinary.uploader().upload(song.getBytes(), options);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Song upload failed");
		}
	}
	
	public List<Map> uploadSongs(List<MultipartFile> songs) {
		List<Map> uploadsong = new ArrayList<Map>();
		
		for(MultipartFile song : songs) {
			try {
				Map result = uploadSong(song);
				uploadsong.add(result);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println(e);
			}
		}
		return uploadsong;
	}
	
	public List<Song> getAllSongs(){
		try {
			Map response = cloudinary.api().resources(
					ObjectUtils.asMap(
							"resource_type", "video",
							"type", "upload",
//							"prefix", "songs/",
							"max_results", 500
							)
					);
			
			List<Map> resources = (List<Map>) response.get("resources");
			System.out.println("reached resources:"+resources.isEmpty());
			List<Song> list = new ArrayList<Song>();
			
			for(Map resource : resources) {
				System.out.println("reached");
				String publicId = (String)resource.get("public_id");
				Song song = new Song();
				song.setPublicId(publicId);
				song.setUrl((String)resource.get("secure_url"));
				song.setTitle(publicId.substring(publicId.lastIndexOf("/")+1));	
				System.out.println(publicId);
				list.add(song);
			}
			songRepository.saveAll(list);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(
                    "Failed to fetch songs"
					);
		}
	}
}
