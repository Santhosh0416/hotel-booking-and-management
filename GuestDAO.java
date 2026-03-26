package com.hotel.dao;

import com.hotel.db.DatabaseConnection;
import com.hotel.model.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Guest entity
 */
public class GuestDAO {
    private static final Logger logger = LoggerFactory.getLogger(GuestDAO.class);

    /**
     * Add a new guest to the database
     */
    public static boolean addGuest(Guest guest) {
        String sql = "INSERT INTO guests (first_name, last_name, email, phone, address, city, state, zip_code, country, id_proof, registration_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, guest.getFirstName());
            pstmt.setString(2, guest.getLastName());
            pstmt.setString(3, guest.getEmail());
            pstmt.setString(4, guest.getPhone());
            pstmt.setString(5, guest.getAddress());
            pstmt.setString(6, guest.getCity());
            pstmt.setString(7, guest.getState());
            pstmt.setString(8, guest.getZipCode());
            pstmt.setString(9, guest.getCountry());
            pstmt.setString(10, guest.getIdProof());
            pstmt.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));

            int rowsAffected = pstmt.executeUpdate();
            logger.info("Guest added successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error adding guest: ", e);
            return false;
        }
    }

    /**
     * Get guest by ID
     */
    public static Guest getGuestById(int guestId) {
        String sql = "SELECT * FROM guests WHERE guest_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guestId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToGuest(rs);
            }

        } catch (SQLException e) {
            logger.error("Error retrieving guest: ", e);
        }
        return null;
    }

    /**
     * Get guest by email
     */
    public static Guest getGuestByEmail(String email) {
        String sql = "SELECT * FROM guests WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToGuest(rs);
            }

        } catch (SQLException e) {
            logger.error("Error retrieving guest by email: ", e);
        }
        return null;
    }

    /**
     * Get all guests
     */
    public static List<Guest> getAllGuests() {
        String sql = "SELECT * FROM guests ORDER BY registration_date DESC";
        List<Guest> guests = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                guests.add(mapResultSetToGuest(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving all guests: ", e);
        }
        return guests;
    }

    /**
     * Update guest information
     */
    public static boolean updateGuest(Guest guest) {
        String sql = "UPDATE guests SET first_name=?, last_name=?, email=?, phone=?, address=?, city=?, state=?, zip_code=?, country=?, id_proof=? WHERE guest_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, guest.getFirstName());
            pstmt.setString(2, guest.getLastName());
            pstmt.setString(3, guest.getEmail());
            pstmt.setString(4, guest.getPhone());
            pstmt.setString(5, guest.getAddress());
            pstmt.setString(6, guest.getCity());
            pstmt.setString(7, guest.getState());
            pstmt.setString(8, guest.getZipCode());
            pstmt.setString(9, guest.getCountry());
            pstmt.setString(10, guest.getIdProof());
            pstmt.setInt(11, guest.getGuestId());

            int rowsAffected = pstmt.executeUpdate();
            logger.info("Guest updated successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error updating guest: ", e);
            return false;
        }
    }

    /**
     * Delete a guest
     */
    public static boolean deleteGuest(int guestId) {
        String sql = "DELETE FROM guests WHERE guest_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, guestId);
            int rowsAffected = pstmt.executeUpdate();
            logger.info("Guest deleted successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error deleting guest: ", e);
            return false;
        }
    }

    /**
     * Map ResultSet to Guest object
     */
    private static Guest mapResultSetToGuest(ResultSet rs) throws SQLException {
        Guest guest = new Guest();
        guest.setGuestId(rs.getInt("guest_id"));
        guest.setFirstName(rs.getString("first_name"));
        guest.setLastName(rs.getString("last_name"));
        guest.setEmail(rs.getString("email"));
        guest.setPhone(rs.getString("phone"));
        guest.setAddress(rs.getString("address"));
        guest.setCity(rs.getString("city"));
        guest.setState(rs.getString("state"));
        guest.setZipCode(rs.getString("zip_code"));
        guest.setCountry(rs.getString("country"));
        guest.setIdProof(rs.getString("id_proof"));
        guest.setRegistrationDate(rs.getTimestamp("registration_date") != null ? 
                rs.getTimestamp("registration_date").toLocalDateTime() : null);
        guest.setLastCheckIn(rs.getTimestamp("last_check_in") != null ? 
                rs.getTimestamp("last_check_in").toLocalDateTime() : null);
        return guest;
    }
}
