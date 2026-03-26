package com.hotel.ui;

import com.hotel.model.Guest;
import com.hotel.dao.GuestDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.dao.BookingDAO;
import com.hotel.model.Room;
import com.hotel.model.Booking;
import com.hotel.service.BookingService;
import com.hotel.util.ValidationUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Main menu class for user interaction
 */
public class MainMenu {
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\n============================================");
            System.out.println("    HOTEL BOOKING AND MANAGEMENT SYSTEM");
            System.out.println("============================================");
            System.out.println("1. Guest Management");
            System.out.println("2. Room Management");
            System.out.println("3. Booking Management");
            System.out.println("4. Exit");
            System.out.println("============================================");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayGuestMenu();
                    break;
                case "2":
                    displayRoomMenu();
                    break;
                case "3":
                    displayBookingMenu();
                    break;
                case "4":
                    System.out.println("Thank you for using Hotel Booking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayGuestMenu() {
        while (true) {
            System.out.println("\n--- Guest Management ---");
            System.out.println("1. Add New Guest");
            System.out.println("2. View Guest Details");
            System.out.println("3. Update Guest");
            System.out.println("4. View All Guests");
            System.out.println("5. Delete Guest");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addGuest();
                    break;
                case "2":
                    viewGuestDetails();
                    break;
                case "3":
                    updateGuest();
                    break;
                case "4":
                    viewAllGuests();
                    break;
                case "5":
                    deleteGuest();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayRoomMenu() {
        while (true) {
            System.out.println("\n--- Room Management ---");
            System.out.println("1. Add New Room");
            System.out.println("2. View Room Details");
            System.out.println("3. View All Rooms");
            System.out.println("4. View Available Rooms");
            System.out.println("5. Update Room");
            System.out.println("6. Delete Room");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addRoom();
                    break;
                case "2":
                    viewRoomDetails();
                    break;
                case "3":
                    viewAllRooms();
                    break;
                case "4":
                    viewAvailableRooms();
                    break;
                case "5":
                    updateRoom();
                    break;
                case "6":
                    deleteRoom();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayBookingMenu() {
        while (true) {
            System.out.println("\n--- Booking Management ---");
            System.out.println("1. Create New Booking");
            System.out.println("2. View Booking Details");
            System.out.println("3. Check In Guest");
            System.out.println("4. Check Out Guest");
            System.out.println("5. Cancel Booking");
            System.out.println("6. View All Bookings");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createBooking();
                    break;
                case "2":
                    viewBookingDetails();
                    break;
                case "3":
                    checkInGuest();
                    break;
                case "4":
                    checkOutGuest();
                    break;
                case "5":
                    cancelBooking();
                    break;
                case "6":
                    viewAllBookings();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Guest Management Methods
    private void addGuest() {
        System.out.println("\n--- Add New Guest ---");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("Zip Code: ");
        String zipCode = scanner.nextLine();
        System.out.print("Country: ");
        String country = scanner.nextLine();
        System.out.print("ID Proof: ");
        String idProof = scanner.nextLine();

        Guest guest = new Guest(firstName, lastName, email, phone);
        guest.setAddress(address);
        guest.setCity(city);
        guest.setState(state);
        guest.setZipCode(zipCode);
        guest.setCountry(country);
        guest.setIdProof(idProof);

        if (GuestDAO.addGuest(guest)) {
            System.out.println("Guest added successfully!");
        } else {
            System.out.println("Failed to add guest.");
        }
    }

    private void viewGuestDetails() {
        System.out.print("Enter Guest ID: ");
        String guestIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(guestIdStr)) {
            System.out.println("Invalid Guest ID.");
            return;
        }

        int guestId = Integer.parseInt(guestIdStr);
        Guest guest = GuestDAO.getGuestById(guestId);

        if (guest != null) {
            System.out.println("\n--- Guest Details ---");
            System.out.println("ID: " + guest.getGuestId());
            System.out.println("Name: " + guest.getFirstName() + " " + guest.getLastName());
            System.out.println("Email: " + guest.getEmail());
            System.out.println("Phone: " + guest.getPhone());
            System.out.println("Address: " + guest.getAddress());
            System.out.println("City: " + guest.getCity());
        } else {
            System.out.println("Guest not found.");
        }
    }

    private void updateGuest() {
        System.out.print("Enter Guest ID: ");
        String guestIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(guestIdStr)) {
            System.out.println("Invalid Guest ID.");
            return;
        }

        int guestId = Integer.parseInt(guestIdStr);
        Guest guest = GuestDAO.getGuestById(guestId);

        if (guest != null) {
            System.out.println("Enter new details (press Enter to skip):");
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) guest.setPhone(phone);

            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) guest.setEmail(email);

            if (GuestDAO.updateGuest(guest)) {
                System.out.println("Guest updated successfully!");
            } else {
                System.out.println("Failed to update guest.");
            }
        } else {
            System.out.println("Guest not found.");
        }
    }

    private void viewAllGuests() {
        List<Guest> guests = GuestDAO.getAllGuests();
        if (guests.isEmpty()) {
            System.out.println("No guests found.");
            return;
        }

        System.out.println("\n--- All Guests ---");
        System.out.printf("%-5s %-15s %-15s %-25s %-12s%n", "ID", "First Name", "Last Name", "Email", "Phone");
        System.out.println("-".repeat(75));
        for (Guest guest : guests) {
            System.out.printf("%-5d %-15s %-15s %-25s %-12s%n", 
                guest.getGuestId(), guest.getFirstName(), guest.getLastName(), 
                guest.getEmail(), guest.getPhone());
        }
    }

    private void deleteGuest() {
        System.out.print("Enter Guest ID: ");
        String guestIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(guestIdStr)) {
            System.out.println("Invalid Guest ID.");
            return;
        }

        int guestId = Integer.parseInt(guestIdStr);
        if (GuestDAO.deleteGuest(guestId)) {
            System.out.println("Guest deleted successfully!");
        } else {
            System.out.println("Failed to delete guest.");
        }
    }

    // Room Management Methods
    private void addRoom() {
        System.out.println("\n--- Add New Room ---");
        System.out.print("Room Number: ");
        String roomNumber = scanner.nextLine();
        
        System.out.println("Room Type: 1-SINGLE, 2-DOUBLE, 3-SUITE, 4-DELUXE");
        System.out.print("Select (1-4): ");
        String typeChoice = scanner.nextLine();
        Room.RoomType roomType = switch (typeChoice) {
            case "1" -> Room.RoomType.SINGLE;
            case "2" -> Room.RoomType.DOUBLE;
            case "3" -> Room.RoomType.SUITE;
            case "4" -> Room.RoomType.DELUXE;
            default -> Room.RoomType.SINGLE;
        };

        System.out.print("Capacity: ");
        String capacityStr = scanner.nextLine();
        int capacity = Integer.parseInt(capacityStr);

        System.out.print("Price Per Night: ");
        String priceStr = scanner.nextLine();
        BigDecimal price = new BigDecimal(priceStr);

        System.out.print("Floor Number: ");
        String floorStr = scanner.nextLine();
        int floor = Integer.parseInt(floorStr);

        Room room = new Room(roomNumber, roomType, capacity, price);
        room.setFloorNumber(floor);

        System.out.print("Has AC (true/false): ");
        room.setHasAC(Boolean.parseBoolean(scanner.nextLine()));
        System.out.print("Has WiFi (true/false): ");
        room.setHasWifi(Boolean.parseBoolean(scanner.nextLine()));
        System.out.print("Has TV (true/false): ");
        room.setHasTV(Boolean.parseBoolean(scanner.nextLine()));
        System.out.print("Has Balcony (true/false): ");
        room.setHasBalcony(Boolean.parseBoolean(scanner.nextLine()));

        if (RoomDAO.addRoom(room)) {
            System.out.println("Room added successfully!");
        } else {
            System.out.println("Failed to add room.");
        }
    }

    private void viewRoomDetails() {
        System.out.print("Enter Room ID: ");
        String roomIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(roomIdStr)) {
            System.out.println("Invalid Room ID.");
            return;
        }

        int roomId = Integer.parseInt(roomIdStr);
        Room room = RoomDAO.getRoomById(roomId);

        if (room != null) {
            System.out.println("\n--- Room Details ---");
            System.out.println("ID: " + room.getRoomId());
            System.out.println("Room Number: " + room.getRoomNumber());
            System.out.println("Type: " + room.getRoomType());
            System.out.println("Status: " + room.getStatus());
            System.out.println("Capacity: " + room.getCapacity());
            System.out.println("Price Per Night: " + room.getPricePerNight());
            System.out.println("Floor: " + room.getFloorNumber());
        } else {
            System.out.println("Room not found.");
        }
    }

    private void viewAllRooms() {
        List<Room> rooms = RoomDAO.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }

        System.out.println("\n--- All Rooms ---");
        System.out.printf("%-5s %-8s %-8s %-10s %-10s %-10s%n", "ID", "Number", "Type", "Status", "Capacity", "Price");
        System.out.println("-".repeat(60));
        for (Room room : rooms) {
            System.out.printf("%-5d %-8s %-8s %-10s %-10d $%-9.2f%n", 
                room.getRoomId(), room.getRoomNumber(), room.getRoomType(), 
                room.getStatus(), room.getCapacity(), room.getPricePerNight());
        }
    }

    private void viewAvailableRooms() {
        List<Room> rooms = RoomDAO.getAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("No available rooms.");
            return;
        }

        System.out.println("\n--- Available Rooms ---");
        System.out.printf("%-5s %-8s %-8s %-10s %-10s %-10s%n", "ID", "Number", "Type", "Capacity", "Price", "Floor");
        System.out.println("-".repeat(60));
        for (Room room : rooms) {
            System.out.printf("%-5d %-8s %-8s %-10d $%-9.2f %-10d%n", 
                room.getRoomId(), room.getRoomNumber(), room.getRoomType(), 
                room.getCapacity(), room.getPricePerNight(), room.getFloorNumber());
        }
    }

    private void updateRoom() {
        System.out.print("Enter Room ID: ");
        String roomIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(roomIdStr)) {
            System.out.println("Invalid Room ID.");
            return;
        }

        int roomId = Integer.parseInt(roomIdStr);
        Room room = RoomDAO.getRoomById(roomId);

        if (room != null) {
            System.out.println("Enter new details (press Enter to skip):");
            System.out.print("Price Per Night: ");
            String priceStr = scanner.nextLine();
            if (!priceStr.isEmpty()) {
                room.setPricePerNight(new BigDecimal(priceStr));
            }

            if (RoomDAO.updateRoom(room)) {
                System.out.println("Room updated successfully!");
            } else {
                System.out.println("Failed to update room.");
            }
        } else {
            System.out.println("Room not found.");
        }
    }

    private void deleteRoom() {
        System.out.print("Enter Room ID: ");
        String roomIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(roomIdStr)) {
            System.out.println("Invalid Room ID.");
            return;
        }

        int roomId = Integer.parseInt(roomIdStr);
        if (RoomDAO.deleteRoom(roomId)) {
            System.out.println("Room deleted successfully!");
        } else {
            System.out.println("Failed to delete room.");
        }
    }

    // Booking Management Methods
    private void createBooking() {
        System.out.println("\n--- Create New Booking ---");
        System.out.print("Guest ID: ");
        String guestIdStr = scanner.nextLine();
        int guestId = Integer.parseInt(guestIdStr);

        System.out.print("Room ID: ");
        String roomIdStr = scanner.nextLine();
        int roomId = Integer.parseInt(roomIdStr);

        System.out.print("Check-in Date (yyyy-MM-dd): ");
        LocalDate checkInDate = LocalDate.parse(scanner.nextLine(), dateFormatter);

        System.out.print("Check-out Date (yyyy-MM-dd): ");
        LocalDate checkOutDate = LocalDate.parse(scanner.nextLine(), dateFormatter);

        System.out.print("Number of Guests: ");
        String numGuestsStr = scanner.nextLine();
        int numberOfGuests = Integer.parseInt(numGuestsStr);

        if (BookingService.createBooking(guestId, roomId, checkInDate, checkOutDate, numberOfGuests)) {
            System.out.println("Booking created successfully!");
        } else {
            System.out.println("Failed to create booking.");
        }
    }

    private void viewBookingDetails() {
        System.out.print("Enter Booking ID: ");
        String bookingIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(bookingIdStr)) {
            System.out.println("Invalid Booking ID.");
            return;
        }

        int bookingId = Integer.parseInt(bookingIdStr);
        Booking booking = BookingDAO.getBookingById(bookingId);

        if (booking != null) {
            System.out.println("\n--- Booking Details ---");
            System.out.println("ID: " + booking.getBookingId());
            System.out.println("Guest ID: " + booking.getGuestId());
            System.out.println("Room ID: " + booking.getRoomId());
            System.out.println("Check-in: " + booking.getCheckInDate());
            System.out.println("Check-out: " + booking.getCheckOutDate());
            System.out.println("Status: " + booking.getStatus());
            System.out.println("Total Price: $" + booking.getTotalPrice());
        } else {
            System.out.println("Booking not found.");
        }
    }

    private void checkInGuest() {
        System.out.print("Enter Booking ID: ");
        String bookingIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(bookingIdStr)) {
            System.out.println("Invalid Booking ID.");
            return;
        }

        int bookingId = Integer.parseInt(bookingIdStr);
        if (BookingService.checkInGuest(bookingId)) {
            System.out.println("Guest checked in successfully!");
        } else {
            System.out.println("Failed to check in guest.");
        }
    }

    private void checkOutGuest() {
        System.out.print("Enter Booking ID: ");
        String bookingIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(bookingIdStr)) {
            System.out.println("Invalid Booking ID.");
            return;
        }

        int bookingId = Integer.parseInt(bookingIdStr);
        if (BookingService.checkOutGuest(bookingId)) {
            System.out.println("Guest checked out successfully!");
        } else {
            System.out.println("Failed to check out guest.");
        }
    }

    private void cancelBooking() {
        System.out.print("Enter Booking ID: ");
        String bookingIdStr = scanner.nextLine();
        
        if (!ValidationUtil.isValidInteger(bookingIdStr)) {
            System.out.println("Invalid Booking ID.");
            return;
        }

        int bookingId = Integer.parseInt(bookingIdStr);
        if (BookingService.cancelBooking(bookingId)) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }

    private void viewAllBookings() {
        List<Booking> bookings = BookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n--- All Bookings ---");
        System.out.printf("%-5s %-8s %-8s %-12s %-12s %-10s %-10s%n", "ID", "Guest", "Room", "Check-in", "Check-out", "Status", "Price");
        System.out.println("-".repeat(75));
        for (Booking booking : bookings) {
            System.out.printf("%-5d %-8d %-8d %-12s %-12s %-10s $%-9.2f%n", 
                booking.getBookingId(), booking.getGuestId(), booking.getRoomId(),
                booking.getCheckInDate(), booking.getCheckOutDate(), 
                booking.getStatus(), booking.getTotalPrice());
        }
    }
}
