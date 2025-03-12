package auca.ac.rw.cinemaTicket.repositories;

import auca.ac.rw.cinemaTicket.models.SeatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<SeatModel, UUID> {
}

