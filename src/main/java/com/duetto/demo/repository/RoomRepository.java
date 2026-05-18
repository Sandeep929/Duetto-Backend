package com.duetto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duetto.demo.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

}