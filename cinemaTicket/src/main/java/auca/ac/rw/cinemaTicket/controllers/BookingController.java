package auca.ac.rw.cinemaTicket.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.services.BookingServices;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {
   
    @Autowired
    private BookingServices bookingServices;

    
    @PostMapping(value = "/saveBooking", consumes = "application/json")
    public ResponseEntity<BookingModel> createBooking(@RequestBody BookingModel booking) {
        // Call the service method to create and save the booking
        BookingModel savedBooking = bookingServices.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
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

    @GetMapping(value = "/allBookings")
    public ResponseEntity<List<BookingModel>> getAllBookings() {
        System.out.println("Fetching all bookings...");
        List<BookingModel> bookings = bookingServices.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    @GetMapping(value = "/usersWhoBookedActionMovies")
    public ResponseEntity<?> getUsersWhoBookedActionMovies() {
        List<BookingModel> bookings = bookingServices.getUsersWhoBookedActionMovies();
        if (!bookings.isEmpty()) {
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No users found who booked action movies", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/bookMovie")
    public ResponseEntity<?> bookMovie(
            @RequestParam UUID userId,
            @RequestParam UUID movieId) {
    
        String result = bookingServices.bookMovie(userId, movieId);
        if (result.equals("Booking successful")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
}
}
