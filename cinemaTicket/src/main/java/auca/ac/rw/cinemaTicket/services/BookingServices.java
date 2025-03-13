package auca.ac.rw.cinemaTicket.services;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private SeatRepository seatRepository;

    //for saving the user booking
       public BookingModel createBooking(BookingModel booking) {
        // Ensure the associated SeatModel is saved first
        if (booking.getSeatModel() != null) {
            seatRepository.save(booking.getSeatModel());
        }

        // Ensure the associated UserModel exists
        if (booking.getUser() != null && booking.getUser().getId() != null) {
            UserModel user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            booking.setUser(user);
        }

        // Ensure the associated MovieModels exist
        if (booking.getMovies() != null && !booking.getMovies().isEmpty()) {
            List<MovieModel> movies = booking.getMovies().stream()
                .map(movie -> movieRepository.findById(movie.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
            booking.setMovies(movies);
        }

        // Save the booking
        return bookingRepository.save(booking);
    }
    

    //for getting all the bookings by show time
    public List<BookingModel> getUsersByShowTime(String showTime) {
        return bookingRepository.findByShowTime(showTime);
    }

    //getAll bookings
    public List<BookingModel> getAllBookings() {
        return bookingRepository.findAll(); 
    }

    public List<BookingModel> getUsersWhoBookedActionMovies() {
        return bookingRepository.findByMoviesCategoryName("ACTION");
    }

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
}
            

