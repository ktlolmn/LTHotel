package com.hotel.web.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.web.DTO.Response;
import com.hotel.web.DTO.RoomDTO;
import com.hotel.web.Entity.Room;
import com.hotel.web.Repos.RoomRepository;
import com.hotel.web.Utils.Utils;
import com.hotel.web.exception.OurException;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public Response getAllRoom() {
        Response response = new Response();
        try {
            List<Room> rooms = roomRepository.findAll();
            List<RoomDTO> roomDTOs = Utils.mapRoomList(rooms);
            response.setStatus(200);
            response.setMessage("Fetch success");
            response.setRoomList(roomDTOs);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error fetching rooms: " + e.getMessage());
        }
        return response;
    }

    public Response getById(int id) {
        Response response = new Response();
        try {
            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new OurException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoom(room);
            response.setRoomDTO(roomDTO);
            response.setStatus(200);
            response.setMessage("Fetch success");
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error fetching room: " + e.getMessage());
        }
        return response;
    }

    public Response updateRoom(int id, String name, double price, String description, boolean status, String type) {
        Response response = new Response();
        try {
            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new OurException("Room Not Found"));

            room.setName(name);
            room.setPrice(price);
            room.setDescription(description);
            room.setStatus(status);
            room.setType(type);
            roomRepository.save(room);

            List<RoomDTO> roomDTOs = Utils.mapRoomList(roomRepository.findAll());
            response.setStatus(200);
            response.setMessage("Update success");
            response.setRoomList(roomDTOs);
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error updating room: " + e.getMessage());
        }
        return response;
    }

    public Response createRoom(RoomDTO roomDTO) {
        Response response = new Response();
        try {
            Room room = new Room(roomDTO.getName(), roomDTO.getPrice(), roomDTO.getType(), roomDTO.getDescription(), roomDTO.isStatus());
            roomRepository.save(room);

            List<RoomDTO> roomDTOs = Utils.mapRoomList(roomRepository.findAll());
            response.setStatus(200);
            response.setMessage("Create success");
            response.setRoomList(roomDTOs);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error creating room: " + e.getMessage());
        }
        return response;
    }
}
