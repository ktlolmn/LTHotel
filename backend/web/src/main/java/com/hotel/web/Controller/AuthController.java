package com.hotel.web.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.hotel.web.DTO.LoginRequest;
import com.hotel.web.DTO.RegisterRequest;
import com.hotel.web.DTO.Response;
import com.hotel.web.Service.UserService;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public ResponseEntity<Response> Login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest requestRegister) {
        Response response = userService.register(requestRegister);
        System.out.println(response);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> changePassword(@RequestBody Map<String, Object> requestBody) {
        String currentPassword = (String) requestBody.get("currentPassword");
        String newPassword = (String) requestBody.get("newPassword");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.changePassword(currentPassword, newPassword, username);
    }  
}