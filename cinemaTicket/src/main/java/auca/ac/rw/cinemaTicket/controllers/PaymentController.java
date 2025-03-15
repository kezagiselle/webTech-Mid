package auca.ac.rw.cinemaTicket.controllers;

import auca.ac.rw.cinemaTicket.models.PaymentModel;
import auca.ac.rw.cinemaTicket.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/addPayment/{bookingId}")
    public ResponseEntity<PaymentModel> addPaymentToBooking(
            @PathVariable UUID bookingId,
            @RequestBody PaymentModel payment) {
        try {
            PaymentModel savedPayment = paymentService.addPaymentToBooking(bookingId, payment);
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
