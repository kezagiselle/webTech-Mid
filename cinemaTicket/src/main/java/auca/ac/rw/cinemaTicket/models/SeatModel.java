package auca.ac.rw.cinemaTicket.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seats") 
public class SeatModel {
   
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "id")
    private UUID id;

    @Column(name = "seatNumber")
    private Integer seatNumber;

    @Column(name = "rowNumber")
    private Integer rowNumber;

    @Column(name = "availableSeats")
    private Boolean availableSeats;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference
    private BookingModel booking;

    public SeatModel(UUID id, Integer seatNumber, Integer rowNumber, Boolean availableSeats) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.availableSeats = availableSeats;
    }

    public SeatModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Boolean getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Boolean availableSeats) {
        this.availableSeats = availableSeats;
    }

    

}
