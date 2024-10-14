package com.hotel.web.Repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.web.Entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    boolean existsByPhone(String phone);
    Optional<Customer> findByEmail(String email);
}
