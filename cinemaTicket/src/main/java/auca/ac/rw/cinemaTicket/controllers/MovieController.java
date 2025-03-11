package auca.ac.rw.cinemaTicket.controllers;

import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.services.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movies") // Base URL for all endpoints in this controller
public class MovieController {

    @Autowired
    private MovieServices movieServices;

    // Endpoint to add a new movie
    @PostMapping("/add")
    public ResponseEntity<MovieModel> addMovie(
            @RequestBody String title,
            @RequestBody CategoryUnit category,
            @RequestBody String duration,
            @RequestBody String language) {
        MovieModel newMovie = movieServices.addMovie(title, category, duration, language);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all movies
    @GetMapping("/all")
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        List<MovieModel> movies = movieServices.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // Endpoint to retrieve a movie by its ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieModel> getMovieById(@PathVariable UUID id) {
        MovieModel movie = movieServices.getMovieById(id);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}