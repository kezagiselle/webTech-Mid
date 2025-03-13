package auca.ac.rw.cinemaTicket.controllers;
import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.models.SeatModel;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.repositories.MovieRepository;
import auca.ac.rw.cinemaTicket.repositories.SeatRepository;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;
import auca.ac.rw.cinemaTicket.services.BookingServices;
import auca.ac.rw.cinemaTicket.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingServices bookingServices;

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

        // Create and save the booking (before seat, so booking gets an ID)
        BookingModel booking = new BookingModel();
        booking.setShowTime(bookingModel.getShowTime());
        booking.setSeat(bookingModel.getSeat());
        booking.setPaymentStatus(bookingModel.getPaymentStatus());
        booking.setUser(user);
        booking.setMovies(List.of(movie));

        // Save the booking first
        BookingModel savedBooking = bookingServices.createBooking(booking);

        // Ensure the seat exists and link it to the booking
        SeatModel seat = bookingModel.getSeatModel();
        if (seat == null) {
            // Create a new SeatModel if none is provided
            seat = new SeatModel();
            seat.setSeatNumber(0);  // Default value (adjust as needed)
            seat.setRowNumber(0);
            seat.setAvailableSeats(false); // Default availability
        } else {
            // Set the booking reference in the seat before saving
            seat.setBooking(savedBooking);
        }

        // Save the seat with the booking_id now set
        seat = seatRepository.save(seat);

        // Update the booking with the seat info (if needed)
        savedBooking.setSeatModel(seat);

        // Return the saved booking
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    } catch (RuntimeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}


    @GetMapping("/getAllSeats")
    public List<SeatModel> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/getSeatById/{id}")
    public SeatModel getSeatById(@PathVariable UUID id) {
        return seatService.getSeatById(id);
    }

    @PutMapping("/updateSeat/{id}")
    public SeatModel updateSeat(@PathVariable UUID id, @RequestBody SeatModel seat) {
        return seatService.updateSeat(id, seat);
    }

    @DeleteMapping("/deleteSeat/{id}")
    public void deleteSeat(@PathVariable UUID id) {
        seatService.deleteSeat(id);
    }
}
