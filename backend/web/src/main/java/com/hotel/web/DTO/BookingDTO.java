package com.hotel.web.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private int id;
    private RoomDTO roomDTO;
    private CustomerDTO customerDTO;
    private LocalDateTime bookingDate;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String status;
    private double fee;
}
