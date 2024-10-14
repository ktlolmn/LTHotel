package com.hotel.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.web.DTO.Response;
import com.hotel.web.DTO.RoomDTO;
import com.hotel.web.Service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired 
    RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAll(){
        Response response = roomService.getAllRoom();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    
    @GetMapping("/room-by-id/{roomId}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getById(@PathVariable int roomId){
        System.out.println("lấy phòng theo id");
        Response response = roomService.getById(roomId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable int roomId, @RequestBody RoomDTO room){
                                            // @RequestParam(value = "name", required = false) String name,
                                            // @RequestParam(value = "price", required = false) Double price,
                                            // @RequestParam(value = "type", required = false) String type,
                                            // @RequestParam(value = "status", required = false) Boolean status,
                                            // @RequestParam(value = "description", required = false) String description) {
        System.out.println("update room" + roomId + roomId + room.getName() + room.getPrice() + room.getDescription() + room.isStatus() + room.getType() +"noi dung");
        Response response = roomService.updateRoom(roomId, room.getName(), room.getPrice(), room.getDescription(), room.isStatus(), room.getType());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createRoom(@RequestBody RoomDTO room){
        System.out.println("create room" + room);
        Response response = roomService.createRoom(room);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
