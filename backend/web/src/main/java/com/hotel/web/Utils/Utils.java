package com.hotel.web.Utils;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.hotel.web.DTO.BookingDTO;
import com.hotel.web.DTO.CustomerDTO;
import com.hotel.web.DTO.FeedbackDTO;
import com.hotel.web.DTO.RoomDTO;
import com.hotel.web.DTO.UserDTO;
import com.hotel.web.Entity.Booking;
import com.hotel.web.Entity.Customer;
import com.hotel.web.Entity.Feedback;
import com.hotel.web.Entity.Room;
import com.hotel.web.Entity.User;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i< length; i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setCreateAt(user.getCreateAt());
        return userDTO;
    }

    public static RoomDTO mapRoom(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getRoomId());
        roomDTO.setName(room.getName());
        roomDTO.setPrice(room.getPrice());
        roomDTO.setStatus(room.isStatus());
        roomDTO.setDescription(room.getDescription());
        roomDTO.setType(room.getType());
        return roomDTO;
    }

    public static CustomerDTO mapCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getCustomerID());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setName(customer.getName());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setUserDTO(mapUser(customer.getUser()));
        customerDTO.setCreateAt(customer.getCreateAt());
        return customerDTO;
    }

    public static BookingDTO mapBooking(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setCustomerDTO(mapCustomer(booking.getCustomer()));
        bookingDTO.setFee(booking.getFee());
        bookingDTO.setRoomDTO(mapRoom(booking.getRoom()));
        bookingDTO.setStatus(booking.getStatus());
        return bookingDTO;
    }

    public static FeedbackDTO mapFeedback(Feedback feedback){
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setId(feedback.getId());
        feedbackDTO.setFeedback(feedback.getFeedback());
        feedbackDTO.setRate(feedback.getRate());
        feedbackDTO.setCreateAt(feedback.getCreateAt());
        feedbackDTO.setBookingDTO(mapBooking(feedback.getBooking()));;
        return feedbackDTO;
    }
    public static List<RoomDTO> mapRoomList(List<Room> rooms){
        return rooms.stream().map(Utils::mapRoom).collect(Collectors.toList());
    }
    public static List<BookingDTO> mapBookingList(List<Booking> bookings){
        return bookings.stream().map(Utils::mapBooking).collect(Collectors.toList());
    }
    public static List<FeedbackDTO> mapFeedbackList(List<Feedback> feedbacks){
        return feedbacks.stream().map(Utils::mapFeedback).collect(Collectors.toList());
    }
}
