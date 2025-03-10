package auca.ac.rw.cinemaTicket.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.cinemaTicket.models.BookingModel;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.BookingRepository;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;

@Service
public class BookingServices {
  
    @Autowired
    private BookingRepository bookingRepository; 

    @Autowired
    private UserRepository userRepository;

    //for saving the user booking
    public String saveUserBooking(BookingModel bookingModel, String names) {
        if (names == null || names.trim().isEmpty()) {
            return "Invalid user name provided";
        }
    
        Optional<UserModel> getUser = userRepository.findByNames(names.trim());
    
        if (getUser.isPresent()) {
            bookingModel.setUser(getUser.get()); 
            bookingRepository.save(bookingModel);
            return "Booking saved successfully";
        } else {
            return "User with the given names is not available";
        }
    }

    //for getting all the bookings by show time
    public List<BookingModel> getUsersByShowTime(String showTime) {
        return bookingRepository.findByShowTime(showTime);
    }

    //getAll bookings
    public List<BookingModel> getAllBookings() {
        return bookingRepository.findAll(); 
    }
            
}
