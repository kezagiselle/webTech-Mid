package auca.ac.rw.cinemaTicket.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.models.SeatModel;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.repositories.MovieRepository;
import auca.ac.rw.cinemaTicket.repositories.SeatRepository;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;
import auca.ac.rw.cinemaTicket.services.BookingServices;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/booking")
public class BookingController {
   
    @Autowired
    private BookingServices bookingServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    
    @PostMapping(value = "/saveBooking", consumes = "application/json")
    public ResponseEntity<BookingModel> createBooking(
        @RequestParam("userId") UUID userId,  
        @RequestBody BookingModel bookingModel) {  
        try {
            // Fetch the UserModel
            UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    
            // Ensure the seat exists
            SeatModel seat = bookingModel.getSeatModel();
            if (seat == null) {
                // Create a new SeatModel if none is provided
                seat = new SeatModel();
                seat.setSeatNumber(0);  
                seat.setRowNumber(0);
                seat.setAvailableSeats(false); 
            }
    
            // Save the seat first (without a booking reference)
            seat = seatRepository.save(seat);
    
            // Create the booking
            BookingModel booking = new BookingModel();
            booking.setShowTime(bookingModel.getShowTime());
            booking.setPaymentStatus(bookingModel.getPaymentStatus());
            booking.setUser(user);
            booking.setSeatModel(seat); // Link the seat to the booking
          
            // Save the booking
          BookingModel savedBooking = bookingRepository.save(booking);
    
            // Link the seat to the booking
            seat.setBooking(savedBooking);  // Set the booking reference in the seat
    
            // Save the seat with the updated booking reference
            seatRepository.save(seat);
    
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping(value = "/getBookings")
    public ResponseEntity<?> getUsersByShowTime(String showTime) {
        List<BookingModel> users = bookingServices.getUsersByShowTime(showTime);
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User booking not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/getBookingById/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable String id) {
        try {
            // Convert the String ID to UUID
            UUID uuid = UUID.fromString(id);
            Optional<BookingModel> booking = bookingRepository.findById(uuid);
    
            // Check if the booking exists
            if (booking.isPresent()) {
                return new ResponseEntity<>(booking.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format
            return new ResponseEntity<>("Invalid booking ID format", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/allBookings")
    public ResponseEntity<List<BookingModel>> getAllBookings() {
        System.out.println("Fetching all bookings...");
        List<BookingModel> bookings = bookingServices.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}

