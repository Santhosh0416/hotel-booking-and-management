package com.hotel.dao;

import com.hotel.db.DatabaseConnection;
import com.hotel.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Room entity
 */
public class RoomDAO {
    private static final Logger logger = LoggerFactory.getLogger(RoomDAO.class);

    /**
     * Add a new room to the database
     */
    public static boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, room_type, status, capacity, price_per_night, description, floor_number, has_ac, has_wifi, has_tv, has_balcony) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getRoomNumber());
            pstmt.setString(2, room.getRoomType().name());
            pstmt.setString(3, room.getStatus().name());
            pstmt.setInt(4, room.getCapacity());
            pstmt.setBigDecimal(5, room.getPricePerNight());
            pstmt.setString(6, room.getDescription());
            pstmt.setInt(7, room.getFloorNumber());
            pstmt.setBoolean(8, room.isHasAC());
            pstmt.setBoolean(9, room.isHasWifi());
            pstmt.setBoolean(10, room.isHasTV());
            pstmt.setBoolean(11, room.isHasBalcony());

            int rowsAffected = pstmt.executeUpdate();
            logger.info("Room added successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error adding room: ", e);
            return false;
        }
    }

    /**
     * Get room by ID
     */
    public static Room getRoomById(int roomId) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRoom(rs);
            }

        } catch (SQLException e) {
            logger.error("Error retrieving room: ", e);
        }
        return null;
    }

    /**
     * Get room by room number
     */
    public static Room getRoomByNumber(String roomNumber) {
        String sql = "SELECT * FROM rooms WHERE room_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRoom(rs);
            }

        } catch (SQLException e) {
            logger.error("Error retrieving room by number: ", e);
        }
        return null;
    }

    /**
     * Get all available rooms
     */
    public static List<Room> getAvailableRooms() {
        String sql = "SELECT * FROM rooms WHERE status = 'AVAILABLE'";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving available rooms: ", e);
        }
        return rooms;
    }

    /**
     * Get all rooms
     */
    public static List<Room> getAllRooms() {
        String sql = "SELECT * FROM rooms ORDER BY room_number";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving all rooms: ", e);
        }
        return rooms;
    }

    /**
     * Get rooms by type
     */
    public static List<Room> getRoomsByType(Room.RoomType roomType) {
        String sql = "SELECT * FROM rooms WHERE room_type = ? ORDER BY room_number";
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomType.name());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving rooms by type: ", e);
        }
        return rooms;
    }

    /**
     * Update room information
     */
    public static boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET room_number=?, room_type=?, status=?, capacity=?, price_per_night=?, description=?, floor_number=?, has_ac=?, has_wifi=?, has_tv=?, has_balcony=? WHERE room_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getRoomNumber());
            pstmt.setString(2, room.getRoomType().name());
            pstmt.setString(3, room.getStatus().name());
            pstmt.setInt(4, room.getCapacity());
            pstmt.setBigDecimal(5, room.getPricePerNight());
            pstmt.setString(6, room.getDescription());
            pstmt.setInt(7, room.getFloorNumber());
            pstmt.setBoolean(8, room.isHasAC());
            pstmt.setBoolean(9, room.isHasWifi());
            pstmt.setBoolean(10, room.isHasTV());
            pstmt.setBoolean(11, room.isHasBalcony());
            pstmt.setInt(12, room.getRoomId());

            int rowsAffected = pstmt.executeUpdate();
            logger.info("Room updated successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error updating room: ", e);
            return false;
        }
    }

    /**
     * Delete a room
     */
    public static boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            int rowsAffected = pstmt.executeUpdate();
            logger.info("Room deleted successfully. Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error deleting room: ", e);
            return false;
        }
    }

    /**
     * Map ResultSet to Room object
     */
    private static Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setRoomNumber(rs.getString("room_number"));
        room.setRoomType(Room.RoomType.valueOf(rs.getString("room_type")));
        room.setStatus(Room.RoomStatus.valueOf(rs.getString("status")));
        room.setCapacity(rs.getInt("capacity"));
        room.setPricePerNight(rs.getBigDecimal("price_per_night"));
        room.setDescription(rs.getString("description"));
        room.setFloorNumber(rs.getInt("floor_number"));
        room.setHasAC(rs.getBoolean("has_ac"));
        room.setHasWifi(rs.getBoolean("has_wifi"));
        room.setHasTV(rs.getBoolean("has_tv"));
        room.setHasBalcony(rs.getBoolean("has_balcony"));
        return room;
    }
}
