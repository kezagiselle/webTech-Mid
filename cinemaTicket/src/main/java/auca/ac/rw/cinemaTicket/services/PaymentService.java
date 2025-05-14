package auca.ac.rw.cinemaTicket.services;
import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.PaymentModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.repositories.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    public PaymentModel addPaymentToBooking(UUID bookingId, PaymentModel payment) {
        if (bookingId != null) {
            BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
            payment.setBooking(booking);
        }
    
        return paymentRepository.save(payment);
    }
        // Get payment by ID
        public PaymentModel getPaymentById(UUID paymentId) {
            return paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));
        }
    
        // Get all payments
        public List<PaymentModel> getAllPayments() {
            return paymentRepository.findAll();
        }
}
