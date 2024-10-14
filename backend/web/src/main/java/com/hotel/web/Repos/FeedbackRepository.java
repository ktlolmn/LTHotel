package com.hotel.web.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.web.Entity.Booking;
import com.hotel.web.Entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>{
    Feedback findByBooking(Booking booking);
}
