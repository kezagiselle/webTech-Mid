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

    @PostMapping(value = "/saveSeats", consumes = "application/json")
    public ResponseEntity<String> saveSeatsForBooking(
        @RequestParam("bookingId") UUID bookingId,  
        @RequestBody List<SeatModel> seats) {  
        try {
            // Fetch the booking by ID
            BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));
    
            // Validate seats
            if (seats == null || seats.isEmpty()) {
                throw new IllegalArgumentException("Seats cannot be null or empty");
            }
    
            // Save each seat and link it to the booking
            for (SeatModel seat : seats) {
                seat.setBooking(booking); // Link the seat to the booking
                seatRepository.save(seat); // Save the seat
            }
    
            return ResponseEntity.status(HttpStatus.CREATED).body("Seats saved successfully for booking ID: " + bookingId);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Failed to save seats: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
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
