package com.hotel.web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.web.DTO.BookingDTO;
import com.hotel.web.DTO.FeedbackDTO;
import com.hotel.web.DTO.Response;
import com.hotel.web.Entity.Booking;
import com.hotel.web.Entity.Feedback;
import com.hotel.web.Entity.Room;
import com.hotel.web.Repos.BookingRepository;
import com.hotel.web.Repos.FeedbackRepository;
import com.hotel.web.Repos.RoomRepository;
import com.hotel.web.Utils.Utils;
import com.hotel.web.exception.OurException;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RoomRepository roomRepository;

    @Transactional
    public Response createFeedback(FeedbackDTO feedbackDTO) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(feedbackDTO.getBookingDTO().getId())
                    .orElseThrow(() -> new OurException("Booking Not Found"));

            booking.setStatus("Paying");
            bookingRepository.save(booking);

            Feedback feedback = new Feedback(feedbackDTO.getRate(), feedbackDTO.getFeedback(), booking);
            feedbackRepository.save(feedback);

            List<BookingDTO> bookingDTOs = Utils.mapBookingList(bookingRepository.findByCustomer(booking.getCustomer()));
            response.setStatus(200);
            response.setMessage("success");
            response.setBookingList(bookingDTOs);

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Creating feedback: " + e.getMessage());
        }

        return response;
    }

    public Response getByBooking(int bookingID) {
        Response response = new Response();

        try {
            Booking booking = bookingRepository.findById(bookingID)
                    .orElseThrow(() -> new OurException("Booking Not Found"));

            Feedback feedback = feedbackRepository.findByBooking(booking);

            if (feedback != null) {
                response.setStatus(200);
                response.setMessage("success");
                FeedbackDTO feedbackDTO = Utils.mapFeedback(feedback);
                response.setFeedbackDTO(feedbackDTO);
            } else {
                response.setStatus(202);
                response.setMessage("No feedback found for this booking");
            }

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Retrieving feedback: " + e.getMessage());
        }

        return response;
    }
    public Response getByRoom(int roomID) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomID)
                    .orElseThrow(()-> new OurException("Room not found"));
            List<Booking> bookingList = bookingRepository.findByRoom(room);
            System.out.println("Số booking : " + bookingList.size());
            if(bookingList.size()>0){
                List<Feedback> feedbacks = new ArrayList<>();
                bookingList.forEach((b)->{
                    Feedback feedback = feedbackRepository.findByBooking(b);
                    if(feedback != null){
                        feedbacks.add(feedback);
                    }
                });
                if (feedbacks.size() > 0) {
                    response.setStatus(200);
                    response.setMessage("Success");
                    response.setFeedbackList(Utils.mapFeedbackList(feedbacks));
                }else{
                    response.setStatus(201);
                    response.setMessage("Feedbacks is empty");
                }
                System.out.println("feedback : " + feedbacks);
            }else{
                response.setStatus(202);
                response.setMessage("Bookings is empty");
            }
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
            System.out.println(e.toString());
        } catch (Exception e) {
            response.setStatus(500);
            System.out.println(e.toString());
            response.setMessage("Error Retrieving feedback: " + e.getMessage());
        }

        return response;
    }
}
