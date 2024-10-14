package com.hotel.web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.web.DTO.CustomerDTO;
import com.hotel.web.DTO.Response;
import com.hotel.web.Entity.Customer;
import com.hotel.web.Repos.CustomerRepository;
import com.hotel.web.Utils.Utils;
import com.hotel.web.exception.OurException;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Response getCustomerInfor(String username){
        Response response = new Response();
        try {
            Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(()-> new OurException("Found not user"));
            response.setStatus(200);
            response.setCustomerDTO(Utils.mapCustomer(customer));
        }
        catch(OurException e){
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            System.out.println(e.toString());
        }
        return response;
    }
    @Transactional
    public Response updateCustomerInfor(String username, CustomerDTO customerDTO){
        Response response = new Response();
        try {
            Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(()-> new OurException("Found not user"));
            customer.setName(customerDTO.getName());
            customer.setPhone(customerDTO.getPhone());
            customer.setEmail(customerDTO.getEmail());
            customerRepository.save(customer);
            response.setStatus(200);
            response.setCustomerDTO(Utils.mapCustomer(customer));
        }
        catch(OurException e){
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            System.out.println(e.toString());
        }
        return response;
    }
}
