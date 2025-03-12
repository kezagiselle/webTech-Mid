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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "seat")
    private String seat;
    
    @Column(name = "payment")
    private String paymentStatus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonBackReference
    private SeatModel seats;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserModel user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference 
    @JoinTable(
        name = "ticket",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<MovieModel> movies;

    public BookingModel() {
    }

    public BookingModel(UUID id, String showTime, String seat, String paymentStatus) {
        this.id = id;
        this.showTime = showTime;
        this.seat = seat;
        this.paymentStatus = paymentStatus;
    }

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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    public SeatModel getSeats() {
        return seats;
    }

    public void setSeats(SeatModel seats) {
        this.seats = seats;
    }
}