package auca.ac.rw.cinemaTicket.services;

import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.SeatModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    
    public SeatModel addSeat(SeatModel seat) {
        // Set the seat as available by default if not provided
        if (seat.getAvailableSeats() == null) {
            seat.setAvailableSeats(true);
        }
    
        // Ensure the booking exists before saving the seat
        if (seat.getBooking() != null && seat.getBooking().getId() != null) {
            BookingModel booking = bookingRepository.findById(seat.getBooking().getId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
            seat.setBooking(booking);
        } else {
            throw new RuntimeException("Booking is required for seat allocation.");
        }
    
        return seatRepository.save(seat);
    }

    public List<SeatModel> getAllSeats() {
        return seatRepository.findAll();
    }

    public SeatModel getSeatById(UUID id) {
        return seatRepository.findById(id).orElse(null);
    }

    public SeatModel updateSeat(UUID id, SeatModel seat) {
        SeatModel existingSeat = seatRepository.findById(id).orElse(null);
        if (existingSeat != null) {
            existingSeat.setSeatNumber(seat.getSeatNumber());
            existingSeat.setRowNumber(seat.getRowNumber());
            existingSeat.setAvailableSeats(seat.getAvailableSeats());
            return seatRepository.save(existingSeat);
        }
        return null;
    }

    public void deleteSeat(UUID id) {
        seatRepository.deleteById(id);
    }
}
