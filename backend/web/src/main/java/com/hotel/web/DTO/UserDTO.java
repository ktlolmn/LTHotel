package com.hotel.web.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createAt;
}
