package com.hotel.web.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int status;
    private String message;
    private String token;
    private String role;

    private String expirationTime;

    private UserDTO userDTO;
    private RoomDTO roomDTO;
    private CustomerDTO customerDTO;
    private BookingDTO bookingDTO;
    private FeedbackDTO feedbackDTO;

    private List<UserDTO> userList;
    private List<RoomDTO> roomList;
    private List<CustomerDTO> customerList;
    private List<FeedbackDTO> feedbackList;
    private List<BookingDTO> bookingList;
}
