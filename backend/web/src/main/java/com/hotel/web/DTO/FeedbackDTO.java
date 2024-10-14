package com.hotel.web.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FeedbackDTO {
    private int id;
    private int rate;
    private String feedback;
    private LocalDateTime createAt;
    private BookingDTO bookingDTO;
}
