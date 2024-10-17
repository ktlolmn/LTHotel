package com.hotel.web.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.web.Entity.Booking;
import com.hotel.web.Entity.Customer;
import com.hotel.web.Entity.Room;

import java.time.LocalDateTime;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Integer>{
    List<Booking> findByCustomer(Customer customer);
    @Query("SELECT r FROM Room r WHERE r.roomId NOT IN " +
           "(SELECT b.room.roomId FROM Booking b " +
           "WHERE b.status != 'CANCELLED' AND " +
           "((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate) OR " +
           "(b.checkInDate >= :checkInDate AND b.checkInDate < :checkOutDate)))")
    List<Room> findAvailableRooms(@Param("checkInDate") LocalDateTime checkInDate,
                                  @Param("checkOutDate") LocalDateTime checkOutDate);
    List<Booking> findByRoom(Room room);
    @Query("SELECT b FROM Booking b WHERE YEAR(b.checkOutDate) = :year")
    List<Booking> findBookingsByYear(@Param("year") int year);
}
