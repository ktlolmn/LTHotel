package com.hotel.web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.web.DTO.BookingDTO;
import com.hotel.web.DTO.FeedbackDTO;
import com.hotel.web.DTO.Response;
import com.hotel.web.Entity.Booking;
import com.hotel.web.Entity.Feedback;
import com.hotel.web.Repos.BookingRepository;
import com.hotel.web.Repos.FeedbackRepository;
import com.hotel.web.Utils.Utils;
import com.hotel.web.exception.OurException;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    BookingRepository bookingRepository;

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
}
