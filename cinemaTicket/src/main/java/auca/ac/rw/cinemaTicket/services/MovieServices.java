package auca.ac.rw.cinemaTicket.services;

import java.util.List;
import java.util.Locale.Category;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.cinemaTicket.models.CategoryUnit;
import auca.ac.rw.cinemaTicket.models.MovieModel;
import auca.ac.rw.cinemaTicket.repositories.MovieRepository;

@Service
public class MovieServices {
    

    @Autowired
    private MovieRepository movieRepository;
    
        public MovieServices(MovieRepository movieRepository) {
            this.movieRepository = movieRepository;
        }
           public MovieModel addMovie(String title, CategoryUnit category, String duration, String language) {
            // Create a new MovieModel object
            MovieModel movie = new MovieModel();
            movie.setTitle(title);
            movie.setCategory(category);
        movie.setDuration(duration);
        movie.setLanguage(language);

        // Save the movie to the database
        return movieRepository.save(movie);
    }
    
    public List<MovieModel> getAllMovies() {
        return movieRepository.findAll();
    }

        public MovieModel getMovieById(UUID id) {
        return movieRepository.findById(id).orElse(null);
    }
}
