package com.hotel.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.web.DTO.FeedbackDTO;
import com.hotel.web.DTO.Response;
import com.hotel.web.Service.FeedbackService;

@RestController
@RequestMapping("/feed-back")
public class FeedbackControler {
    @Autowired
    FeedbackService feedbackService;
    
    @GetMapping("/get-by-room/{roomID}")
    public ResponseEntity<Response> getByRoomID(@PathVariable int roomID){
        System.out.println("getFeedback room: "+ roomID);
        Response response = feedbackService.getByRoom(roomID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> create(@RequestBody FeedbackDTO feedbackDTO){
        System.out.println("create "+ feedbackDTO);
        Response response = feedbackService.createFeedback(feedbackDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get-by-booking/{bookingID}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> getByBookingID(@PathVariable int bookingID){
        System.out.println("getFeedback "+ bookingID);
        Response response = feedbackService.getByBooking(bookingID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
