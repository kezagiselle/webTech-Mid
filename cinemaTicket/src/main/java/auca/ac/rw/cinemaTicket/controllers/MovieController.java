package auca.ac.rw.cinemaTicket.controllers;

import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.services.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private BookingRepository bookingRepository;

   @PostMapping("/addMovie")
public ResponseEntity<MovieModel> addMovie(
        @RequestParam UUID bookingId, 
        @RequestParam String title,
        @RequestParam CategoryUnit category,
        @RequestParam String duration,
        @RequestParam String language) {

    // Fetch the booking by bookingId
    Optional<BookingModel> bookingOptional = bookingRepository.findById(bookingId);

    if (bookingOptional.isPresent()) {
        BookingModel booking = bookingOptional.get();
        // Create the new movie
        MovieModel newMovie = movieServices.addMovie(bookingId, title, category, duration, language);

        // Associate the movie with the booking
        booking.addMovie(newMovie);
        // Save the updated booking
        bookingRepository.save(booking);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @GetMapping("/allMovies")
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        List<MovieModel> movies = movieServices.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/getMovieById{id}")
    public ResponseEntity<MovieModel> getMovieById(@PathVariable UUID id) {
        MovieModel movie = movieServices.getMovieById(id);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}