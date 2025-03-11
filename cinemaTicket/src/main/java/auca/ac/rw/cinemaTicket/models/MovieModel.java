package auca.ac.rw.cinemaTicket.models;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class MovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title_movie")
    private String title;

    @ManyToOne // Establish a relationship with CategoryUnit
    @JoinColumn(name = "category_id") // Foreign key column in the movies table
    private CategoryUnit category; // Use the entity here

    @Column(name = "duration")
    private String duration;

    @Column(name = "language")
    private String language;

    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<BookingModel> bookings;

    public MovieModel(UUID id, String title, CategoryUnit category, String duration, String language) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.duration = duration;
        this.language = language;
    }

    public MovieModel() {
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryUnit getCategory() {
        return category;
    }

    public void setCategory(CategoryUnit category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<BookingModel> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingModel> bookings) {
        this.bookings = bookings;
    }
}