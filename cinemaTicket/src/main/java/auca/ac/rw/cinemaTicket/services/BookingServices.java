package auca.ac.rw.cinemaTicket.services;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
// import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.models.SeatModel;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.repositories.MovieRepository;
import auca.ac.rw.cinemaTicket.repositories.SeatRepository;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;

@Service
public class BookingServices {
  
    @Autowired
    private BookingRepository bookingRepository; 

    @Autowired
    private MovieRepository movieRepository; 

    @Autowired
    private UserRepository userRepository;

    public class BookingService {

        @Autowired
        private SeatRepository seatRepository;
    
        @Autowired
        private BookingRepository bookingRepository;
    
        public String createBooking(BookingModel booking, SeatModel seat) {
            try {
                // Validate input
                if (booking == null || seat == null) {
                    throw new IllegalArgumentException("Booking and Seat cannot be null");
                }
        
                // Step 1: Save the SeatModel without a booking_id
                seat.setBooking(null); // Ensure booking is null
                seat = seatRepository.save(seat); // Save and update the seat object
        
                // Step 2: Save the BookingModel with the seat_id
                booking.setSeatModel(seat);
                booking = bookingRepository.save(booking); // Save and update the booking object
        
                // Step 3: Update the SeatModel with the booking_id
                seat.setBooking(booking);
                seatRepository.save(seat); // Save the updated seat object
        
                return "Booking created successfully with ID: " + booking.getId();
            } catch (Exception ex) {
                // Log the error and return a meaningful message
                ex.printStackTrace();
                return "Failed to create booking: " + ex.getMessage();
            }
        }
    }

    public BookingModel getBookingById(UUID id) {
        // Assuming you have a repository or data access layer to fetch the booking
        return bookingRepository.findById(id).orElse(null);
    }
    

    //for getting all the bookings by show time
    public List<BookingModel> getUsersByShowTime(String showTime) {
        return bookingRepository.findByShowTime(showTime);
    }

    //getAll bookings
    public List<BookingModel> getAllBookings() {
        return bookingRepository.findAll(); 
    }

    // public List<BookingModel> getUsersWhoBookedActionMovies() {
    //     return bookingRepository.findByMoviesCategoryName("ACTION");
    // }

    public String bookMovie(UUID userId, UUID movieId) {
        // Fetch the user by ID
        Optional<UserModel> userOptional = userRepository.findById(userId);
    
        if (userOptional.isEmpty()) {
            return "User not found";
        }
    
        UserModel user = userOptional.get();
    
        // Fetch the movie by ID
        Optional<MovieModel> movieOptional = movieRepository.findById(movieId);
    
        if (movieOptional.isEmpty()) {
            return "Movie not found";
        }
    
        MovieModel movie = movieOptional.get();
    
        // Create a new booking
        BookingModel booking = new BookingModel();
        booking.setUser(user); // Set the UserModel object
        booking.setMovies(List.of(movie)); // Set the single movie
    
        // Save the booking
        bookingRepository.save(booking);
    
        return "Booking successful";
    }

    public BookingModel save(BookingModel booking) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }


}
            

