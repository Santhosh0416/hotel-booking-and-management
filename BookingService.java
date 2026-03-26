package com.hotel.service;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Booking;
import com.hotel.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service class for booking operations
 */
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    /**
     * Create a new booking
     */
    public static boolean createBooking(int guestId, int roomId, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests) {
        // Validate inputs
        if (checkInDate.isAfter(checkOutDate) || checkInDate.equals(checkOutDate)) {
            logger.warn("Invalid dates: check-in date must be before check-out date");
            return false;
        }

        // Check room availability
        Room room = RoomDAO.getRoomById(roomId);
        if (room == null || room.getStatus() != Room.RoomStatus.AVAILABLE) {
            logger.warn("Room is not available");
            return false;
        }

        // Check room capacity
        if (room.getCapacity() < numberOfGuests) {
            logger.warn("Room capacity is less than number of guests");
            return false;
        }

        // Create booking
        Booking booking = new Booking(guestId, roomId, checkInDate, checkOutDate, numberOfGuests);
        
        // Calculate total price
        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(numberOfNights));
        booking.setTotalPrice(totalPrice);

        // Save booking
        if (BookingDAO.addBooking(booking)) {
            // Update room status to reserved
            room.setStatus(Room.RoomStatus.RESERVED);
            RoomDAO.updateRoom(room);
            logger.info("Booking created successfully");
            return true;
        }

        return false;
    }

    /**
     * Check in a guest
     */
    public static boolean checkInGuest(int bookingId) {
        Booking booking = BookingDAO.getBookingById(bookingId);
        if (booking == null) {
            logger.warn("Booking not found");
            return false;
        }

        // Update booking status
        booking.setStatus(Booking.BookingStatus.CHECKED_IN);
        if (BookingDAO.updateBooking(booking)) {
            // Update room status
            Room room = RoomDAO.getRoomById(booking.getRoomId());
            if (room != null) {
                room.setStatus(Room.RoomStatus.OCCUPIED);
                RoomDAO.updateRoom(room);
            }
            logger.info("Guest checked in: Booking ID " + bookingId);
            return true;
        }

        return false;
    }

    /**
     * Check out a guest
     */
    public static boolean checkOutGuest(int bookingId) {
        Booking booking = BookingDAO.getBookingById(bookingId);
        if (booking == null) {
            logger.warn("Booking not found");
            return false;
        }

        // Update booking status
        booking.setStatus(Booking.BookingStatus.CHECKED_OUT);
        if (BookingDAO.updateBooking(booking)) {
            // Update room status to available
            Room room = RoomDAO.getRoomById(booking.getRoomId());
            if (room != null) {
                room.setStatus(Room.RoomStatus.AVAILABLE);
                RoomDAO.updateRoom(room);
            }
            logger.info("Guest checked out: Booking ID " + bookingId);
            return true;
        }

        return false;
    }

    /**
     * Cancel a booking
     */
    public static boolean cancelBooking(int bookingId) {
        Booking booking = BookingDAO.getBookingById(bookingId);
        if (booking == null) {
            logger.warn("Booking not found");
            return false;
        }

        // Update booking status
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        if (BookingDAO.updateBooking(booking)) {
            // Update room status to available
            Room room = RoomDAO.getRoomById(booking.getRoomId());
            if (room != null && room.getStatus() == Room.RoomStatus.RESERVED) {
                room.setStatus(Room.RoomStatus.AVAILABLE);
                RoomDAO.updateRoom(room);
            }
            logger.info("Booking cancelled: Booking ID " + bookingId);
            return true;
        }

        return false;
    }

    /**
     * Get bookings for a guest
     */
    public static List<Booking> getGuestBookings(int guestId) {
        return BookingDAO.getBookingsByGuest(guestId);
    }

    /**
     * Get all bookings
     */
    public static List<Booking> getAllBookings() {
        return BookingDAO.getAllBookings();
    }

    /**
     * Calculate booking price
     */
    public static BigDecimal calculateBookingPrice(int roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        Room room = RoomDAO.getRoomById(roomId);
        if (room == null) {
            return BigDecimal.ZERO;
        }

        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return room.getPricePerNight().multiply(BigDecimal.valueOf(numberOfNights));
    }
}
