package auca.ac.rw.cinemaTicket.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "category")
    private String category;

    @Column(name = "duration")
    private String duration;

    @Column(name = "language")
    private String language;
}
