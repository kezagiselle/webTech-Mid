package auca.ac.rw.cinemaTicket.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class BookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "showTime")
    private String showTime;

    @Column(name = "payment")
    private String paymentStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonBackReference
    @JsonIgnore
    private SeatModel seatModel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ticket",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<MovieModel> movies;

    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    @JsonManagedReference // Prevents infinite recursion in JSON serialization
    private PaymentModel payment;

    // Constructors, Getters, and Setters
    public BookingModel() {
    }

    public BookingModel(UUID id, String showTime, String seat, String paymentStatus) {
        this.id = id;
        this.showTime = showTime;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public SeatModel getSeatModel() {
        return seatModel;
    }

    public void setSeatModel(SeatModel seatModel) {
        this.seatModel = seatModel;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    // Add a method to associate a movie with this booking
    public void addMovie(MovieModel movie) {
        if (movies == null) {
            movies = new ArrayList<>();
        }
        movies.add(movie);

        // Ensure bidirectional relationship is maintained
        if (movie.getBookings() == null) {
            movie.setBookings(new ArrayList<>());
        }
        movie.getBookings().add(this);
    }

    public PaymentModel getPayment() {
        return payment;
    }

    public void setPayment(PaymentModel payment) {
        this.payment = payment;
        payment.setBooking(this); // Maintain bidirectional relationship
    }

}