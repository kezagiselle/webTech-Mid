package auca.ac.rw.cinemaTicket.controllers;

import java.util.List;

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
    public ResponseEntity<?> saveUserBooking(@RequestBody BookingModel bookingModel,
                                                     @RequestParam String names) {

        String saveBooking = bookingServices.saveUserBooking(bookingModel, names);
        if (saveBooking.equalsIgnoreCase("Booking saved successfully")) {
            return new ResponseEntity<>(saveBooking, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(saveBooking, HttpStatus.NOT_FOUND);
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

    @GetMapping(value = "/allBookings")
    public ResponseEntity<List<BookingModel>> getAllBookings() {
        System.out.println("Fetching all bookings...");
        List<BookingModel> bookings = bookingServices.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}
