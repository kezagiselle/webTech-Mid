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
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.models.SeatModel;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.MovieRepository;
import auca.ac.rw.cinemaTicket.repositories.SeatRepository;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;
import auca.ac.rw.cinemaTicket.services.BookingServices;

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

    
    @PostMapping(value = "/saveBooking", consumes = "application/json")
    public ResponseEntity<BookingModel> createBooking(
        @RequestParam("userId") UUID userId,  
        @RequestParam("movieId") UUID movieId,  
        @RequestBody BookingModel bookingModel) {  
        try {
            // Fetch the UserModel
            UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    
            // Fetch the MovieModel
            MovieModel movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));
    
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
            booking.setMovies(List.of(movie));
            booking.setSeatModel(seat); // Link the seat to the booking
    
            // Save the booking
            BookingModel savedBooking = bookingServices.createBooking(booking);
    
            // Link the seat to the booking (bidirectional relationship)
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

    // @GetMapping(value = "/allBookings")
    // public ResponseEntity<List<BookingModel>> getAllBookings() {
    //     System.out.println("Fetching all bookings...");
    //     List<BookingModel> bookings = bookingServices.getAllBookings();
    //     return ResponseEntity.ok(bookings);
    // }
    // @GetMapping(value = "/usersWhoBookedActionMovies")
    // public ResponseEntity<?> getUsersWhoBookedActionMovies() {
    //     List<BookingModel> bookings = bookingServices.getUsersWhoBookedActionMovies();
    //     if (!bookings.isEmpty()) {
    //         return new ResponseEntity<>(bookings, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>("No users found who booked action movies", HttpStatus.NOT_FOUND);
    //     }
    // }

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

