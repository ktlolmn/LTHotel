package com.hotel.web.DTO;

import lombok.Data;

@Data
public class RoomDTO {
    private int id;
    private String name;
    private String type;
    private double price;
    private String description;
    private boolean status;
}
