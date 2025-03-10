package auca.ac.rw.cinemaTicket.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.ac.rw.cinemaTicket.models.BookingModel;
// import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.services.BookingServices;

@Repository
public interface BookingRepository  extends JpaRepository<BookingModel, UUID>{
    List<BookingModel> findByShowTime(String showTime);

    void save(BookingServices bookingServices);
}
