package auca.ac.rw.cinemaTicket.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.ac.rw.cinemaTicket.models.BookingModel;
// import auca.ac.rw.cinemaTicket.models.UserModel;
// import auca.ac.rw.cinemaTicket.services.BookingServices;
@Repository
public interface BookingRepository extends JpaRepository<BookingModel, UUID> {

    // Find bookings by show time
    List<BookingModel> findByShowTime(String showTime);
    // Find bookings by user ID
    List<BookingModel> findByUserId(UUID userId);
    Optional<BookingModel> findById(UUID id);
}
