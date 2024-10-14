package com.hotel.web.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.web.Entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>{
}
