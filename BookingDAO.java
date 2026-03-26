package com.hotel.dao;

import com.hotel.db.DatabaseConnection;
import com.hotel.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Booking entity
 */
public class BookingDAO {
    private static final Logger logger = LoggerFactory.getLogger(BookingDAO.class);

    /**
     * Add a new booking to the database
     */
    public static boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (guest_id, room_id, check_in_date, check_out_date, number_of_guests, status, total_price, advance_paid, special_requests, booking_date, payment_method) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getGuestId());
            pstmt.setInt(2, booking.getRoomId());
            pstmt.setDate(3, java.sql.Date.valueOf(booking.getCheckInDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(booking.getCheckOutDate()));
            pstmt.setInt(5, booking.getNumberOfGuests());
            pstmt.setString(6, booking.getStatus().name());
            pstmt.setBigDecimal(7, booking.getTotalPrice());
            pstmt.setBigDecimal(8, booking.getAdvancePaid());
            pstmt.setString(9, booking.getSpecialRequests());
            pstmt.setTimestamp(10, Timestamp.valueOf(booking.getBookingDate() != null ? booking.getBookingDate() : LocalDateTime.now()));
            pstmt.setString(11, booking.getPaymentMethod());

            int rowsAffected = pstmt.executeUpdate();
            logger.info("Booking added successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error adding booking: ", e);
            return false;
        }
    }

    /**
     * Get booking by ID
     */
    public static Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }

        } catch (SQLException e) {
            logger.error("Error retrieving booking: ", e);
        }
        return null;
    }

    /**
     * Get all bookings for a guest
     */
    public static List<Booking> getBookingsByGuest(int guestId) {
        String sql = "SELECT * FROM bookings WHERE guest_id = ? ORDER BY booking_date DESC";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guestId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving bookings by guest: ", e);
        }
        return bookings;
    }

    /**
     * Get all bookings for a room
     */
    public static List<Booking> getBookingsByRoom(int roomId) {
        String sql = "SELECT * FROM bookings WHERE room_id = ? ORDER BY check_in_date DESC";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving bookings by room: ", e);
        }
        return bookings;
    }

    /**
     * Get all bookings
     */
    public static List<Booking> getAllBookings() {
        String sql = "SELECT * FROM bookings ORDER BY booking_date DESC";
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving all bookings: ", e);
        }
        return bookings;
    }

    /**
     * Update booking information
     */
    public static boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET guest_id=?, room_id=?, check_in_date=?, check_out_date=?, number_of_guests=?, status=?, total_price=?, advance_paid=?, special_requests=?, payment_method=? WHERE booking_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getGuestId());
            pstmt.setInt(2, booking.getRoomId());
            pstmt.setDate(3, java.sql.Date.valueOf(booking.getCheckInDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(booking.getCheckOutDate()));
            pstmt.setInt(5, booking.getNumberOfGuests());
            pstmt.setString(6, booking.getStatus().name());
            pstmt.setBigDecimal(7, booking.getTotalPrice());
            pstmt.setBigDecimal(8, booking.getAdvancePaid());
            pstmt.setString(9, booking.getSpecialRequests());
            pstmt.setString(10, booking.getPaymentMethod());
            pstmt.setInt(11, booking.getBookingId());

            int rowsAffected = pstmt.executeUpdate();
            logger.info("Booking updated successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error updating booking: ", e);
            return false;
        }
    }

    /**
     * Delete a booking
     */
    public static boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            logger.info("Booking deleted successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error deleting booking: ", e);
            return false;
        }
    }

    /**
     * Map ResultSet to Booking object
     */
    private static Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setGuestId(rs.getInt("guest_id"));
        booking.setRoomId(rs.getInt("room_id"));
        booking.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        booking.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        booking.setNumberOfGuests(rs.getInt("number_of_guests"));
        booking.setStatus(Booking.BookingStatus.valueOf(rs.getString("status")));
        booking.setTotalPrice(rs.getBigDecimal("total_price"));
        booking.setAdvancePaid(rs.getBigDecimal("advance_paid"));
        booking.setSpecialRequests(rs.getString("special_requests"));
        booking.setBookingDate(rs.getTimestamp("booking_date") != null ? 
                rs.getTimestamp("booking_date").toLocalDateTime() : null);
        booking.setPaymentMethod(rs.getString("payment_method"));
        return booking;
    }
}
