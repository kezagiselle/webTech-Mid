package auca.ac.rw.cinemaTicket.controllers;
import auca.ac.rw.cinemaTicket.models.SeatModel;
import auca.ac.rw.cinemaTicket.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/addSeats")
    public SeatModel addSeat(@RequestBody SeatModel seat) {
        return seatService.addSeat(seat);
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
