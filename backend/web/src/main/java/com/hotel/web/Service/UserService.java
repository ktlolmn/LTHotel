package com.hotel.web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.web.DTO.Response;
import com.hotel.web.Entity.Customer;
import com.hotel.web.Entity.User;
import com.hotel.web.Repos.CustomerRepository;
import com.hotel.web.Repos.UserRepository;
import com.hotel.web.Utils.JwtUtils;
import com.hotel.web.Utils.Utils;
import com.hotel.web.exception.OurException;

import jakarta.transaction.Transactional;

import com.hotel.web.DTO.LoginRequest;
import com.hotel.web.DTO.RegisterRequest;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository; 

    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new OurException("Username not Found"));
            String token = jwtUtils.generateToken(user); 
            response.setStatus(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("success");
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
            response.setStatus(500);
            response.setMessage("Username or password incorrect ");
        }
        return response;
    }

    @Transactional
    public Response register(RegisterRequest registerRequest) {
        Response response = new Response();
        try {
            if(customerRepository.existsByPhone(registerRequest.getPhone())){
                throw new OurException(registerRequest.getPhone() + " Already exists");
            }
            String password = passwordEncoder.encode(registerRequest.getPassword());
            User user = new User(registerRequest.getEmail(), password, "USER");
            userRepository.save(user);
            Customer customer = new Customer(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPhone(), user);
            customerRepository.save(customer);
            response.setUserDTO(Utils.mapUser(user));
            response.setStatus(200);
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ResponseEntity<Response> changePassword(String currentPassword, String newPassword, String username) {
        Response response = new Response();
        try {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new OurException("User not found"));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, currentPassword));
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            userRepository.save(user);
            response.setStatus(200);
            response.setMessage("Password changed successfully");
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Current password is incorrect or another error occurred");
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
