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
        // Find the booking by bookingId
        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        // Associate the payment with the booking
        booking.setPayment(payment);

        // Save the booking (cascade will save the payment as well)
        bookingRepository.save(booking);

        return payment;
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
