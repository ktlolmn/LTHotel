package com.hotel.web.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomerDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private UserDTO userDTO;
    private LocalDateTime createAt;
}
