package com.hotel.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import com.hotel.web.DTO.BookingDTO;
import com.hotel.web.DTO.Response;
import com.hotel.web.Service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> createBooking(@RequestBody BookingDTO bookingDTO){
        System.out.println("Tạo booking");
        System.out.println("Dữ liệu" + bookingDTO);
        Response response = bookingService.createBooking(bookingDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAll(){
        System.out.println("Lấy tất cả booking");
        Response response = bookingService.getAll();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get-by-year/{year}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getByYear(@PathVariable int year){
        System.out.println("Lấy tất theo năm");
        Response response = bookingService.getByYear(year);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/get-by-customer")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> getByCustomer(){
        System.out.println("Lấy lịch sử booking");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Lấy lịch sử booking" + authentication.getName());
        Response response = bookingService.getByCustomer(username);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/vacancy")
    public ResponseEntity<Response> getEmtyRoom(@RequestBody BookingDTO bookingDTO){
        Response response = bookingService.getByVacancy(bookingDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/pay-ment/{bookingID}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> payment(@PathVariable int bookingID){
        System.out.println("Payment "+ bookingID);
        Response response = bookingService.paymentBooking(bookingID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/check-in/{bookingID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> checkin(@PathVariable int bookingID){
        System.out.println("Payment "+ bookingID);
        Response response = bookingService.checkInBooking(bookingID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/pay-ment-confirm/{bookingID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> payConfirm(@PathVariable int bookingID){
        System.out.println("Payment "+ bookingID);
        Response response = bookingService.payConfirmBooking(bookingID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/cleaned/{bookingID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> cleanedBooking(@PathVariable int bookingID){
        System.out.println("Payment "+ bookingID);
        Response response = bookingService.cleanedBooking(bookingID);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
