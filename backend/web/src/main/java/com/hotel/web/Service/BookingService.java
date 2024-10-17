package com.hotel.web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.web.DTO.BookingDTO;
import com.hotel.web.DTO.Response;
import com.hotel.web.Entity.Booking;
import com.hotel.web.Entity.Customer;
import com.hotel.web.Entity.Room;
import com.hotel.web.Repos.BookingRepository;
import com.hotel.web.Repos.CustomerRepository;
import com.hotel.web.Repos.RoomRepository;
import com.hotel.web.Utils.Utils;
import com.hotel.web.exception.OurException;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Response createBooking(BookingDTO bookingDTO) {
        Response response = new Response();

        try {
            if (bookingDTO.getCheckOutDate().isBefore(bookingDTO.getCheckInDate())) {
                throw new IllegalArgumentException("Check-in date must come after check-out date");
            }

            List<Room> availableRooms = bookingRepository.findAvailableRooms(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
            Room room = roomRepository.findById(bookingDTO.getRoomDTO().getId())
                    .orElseThrow(() -> new OurException("Room Not Found"));

            if (!availableRooms.contains(room)) {
                throw new OurException("Room not available for selected date range");
            }

            Customer customer = customerRepository.findByEmail(bookingDTO.getCustomerDTO().getEmail())
                    .orElseThrow(() -> new OurException("Customer Not Found"));

            Booking booking = new Booking(room, customer, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate(), "Reserved");
            bookingRepository.save(booking);
            response.setStatus(200);
            response.setMessage("success");

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Creating a booking: " + e.getMessage());
        }

        return response;
    }

    public Response getByCustomer(String username) {
        Response response = new Response();
        try {
            Customer customer = customerRepository.findByEmail(username)
                    .orElseThrow(() -> new OurException("Customer Not Found"));
            List<Booking> bookings = bookingRepository.findByCustomer(customer);
            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookings);
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Retrieving customer bookings: " + e.getMessage());
        }

        return response;
    }

    public Response getAll() {
        Response response = new Response();

        try {
            List<Booking> bookings = bookingRepository.findAll();
            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookings);
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Retrieving all bookings: " + e.getMessage());
        }

        return response;
    }

    public Response getByVacancy(BookingDTO bookingDTO) {
        Response response = new Response();

        try {
            List<Room> rooms = bookingRepository.findAvailableRooms(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
            response.setStatus(200);
            response.setMessage("success");
            response.setRoomList(Utils.mapRoomList(rooms));

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Retrieving available rooms: " + e.getMessage());
        }

        return response;
    }

    public Response paymentBooking(int bookingID) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingID)
                    .orElseThrow(() -> new OurException("Booking Not Found"));

            booking.setStatus("Paying");
            bookingRepository.save(booking);
            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookingRepository.findByCustomer(booking.getCustomer()));
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Processing payment: " + e.getMessage());
        }

        return response;
    }

    public Response checkInBooking(int bookingID) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingID)
                    .orElseThrow(() -> new OurException("Booking Not Found"));

            booking.setStatus("Renting");
            bookingRepository.save(booking);
            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookingRepository.findAll());
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Processing payment: " + e.getMessage());
        }

        return response;
    }

    @Transactional
    public Response payConfirmBooking(int bookingID) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingID)
                    .orElseThrow(() -> new OurException("Booking Not Found"));
            booking.setStatus("Cleaning");
            Room room = booking.getRoom();
            room.setStatus(false);
            roomRepository.save(room);
            bookingRepository.save(booking);
            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookingRepository.findAll());
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Processing payment: " + e.getMessage());
        }

        return response;
    }

    public Response cleanedBooking(int bookingID) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingID)
                    .orElseThrow(() -> new OurException("Booking Not Found"));
            booking.setStatus("Booked");
            Room room = booking.getRoom();
            room.setStatus(true);
            roomRepository.save(room);
            bookingRepository.save(booking);
            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookingRepository.findAll());
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Processing payment: " + e.getMessage());
        }

        return response;
    }

    public Response getByYear(int year) {
        Response response = new Response();
        try {
            List<Booking> bookings = bookingRepository.findBookingsByYear(year);
            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookings);
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error fetch bookings: " + e.getMessage());
        }

        return response;
    }
}
