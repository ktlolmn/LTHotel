package com.hotel.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.web.DTO.CustomerDTO;
import com.hotel.web.DTO.Response;
import com.hotel.web.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @GetMapping("/customer-infor")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> getCustomerInfor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Response response = customerService.getCustomerInfor(username);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PostMapping("/update-customer-infor")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> updateCustomer(@RequestBody CustomerDTO customerDTO){
        System.out.println(customerDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Response response = customerService.updateCustomerInfor(username, customerDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
