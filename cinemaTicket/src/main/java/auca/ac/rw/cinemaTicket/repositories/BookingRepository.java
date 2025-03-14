package auca.ac.rw.cinemaTicket.repositories;

import java.util.List;
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

    // Find bookings by category name (if still needed)
    @Query("SELECT DISTINCT b FROM BookingModel b JOIN b.movies m JOIN m.category c WHERE c.name = :categoryName")
    List<BookingModel> findByMoviesCategoryName(@Param("categoryName") String categoryName);
}
