package auca.ac.rw.cinemaTicket.services;

import java.util.List;
import java.util.Optional;
// import java.util.Locale.Category;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.repositories.MovieRepository;

@Service
public class MovieServices {
    

    @Autowired
    private MovieRepository movieRepository;
    
        public MovieServices(MovieRepository movieRepository) {
            this.movieRepository = movieRepository;
        }
         @Autowired
    private BookingRepository bookingRepository;

    public MovieModel addMovie(UUID bookingId, String title, CategoryUnit category, String duration, String language) {
        // Fetch the booking by bookingId
        Optional<BookingModel> bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isPresent()) {
            BookingModel booking = bookingOptional.get();

            // Create a new MovieModel object
            MovieModel movie = new MovieModel();
            movie.setTitle(title);
            movie.setCategory(category);
            movie.setDuration(duration);
            movie.setLanguage(language);

            // Associate the movie with the booking
            movie.setBooking(booking); // Assuming MovieModel has a setBooking method

            // Save the movie to the database
            return movieRepository.save(movie);
        } else {
            // Handle case where booking is not found
            throw new RuntimeException("Booking not found with ID: " + bookingId);
        }
    }
}
    
    public List<MovieModel> getAllMovies() {
        return movieRepository.findAll();
    }

        public MovieModel getMovieById(UUID id) {
        return movieRepository.findById(id).orElse(null);
    }
}
