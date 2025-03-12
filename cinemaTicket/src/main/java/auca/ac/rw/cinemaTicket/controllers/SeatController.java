package auca.ac.rw.cinemaTicket.controllers;
import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.SeatModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
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

      public ResponseEntity<SeatModel> addSeat(
            @PathVariable("bookingId") UUID bookingId,  // Get the bookingId from the URL parameter
            @RequestBody SeatModel seat) {  // Get the seat details from the request body
        try {
            // Find the BookingModel by ID using the bookingId passed in the URL
            BookingModel booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            // Set the booking to the seat
            seat.setBooking(booking);

            // Save the seat and return the response
            SeatModel savedSeat = seatService.addSeat(seat);
            return new ResponseEntity<>(savedSeat, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
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
