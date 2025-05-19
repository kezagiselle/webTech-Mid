package auca.ac.rw.cinemaTicket.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;

@Service
public class UserServices {
   
    @Autowired
     public UserRepository userRepository;

     public String saveUser(UserModel user) {
        Optional<UserModel> checkUser = userRepository.findByNames(
            user.getNames());

        if (checkUser.isPresent()) {
            return "User already exists";
        } else {
            userRepository.save(user);
            return "User created successfully";
        }
    }

     //getAll
    public List<UserModel> getAllUsers() {
        return userRepository.findAll(); 
    }
    //getById
     public Optional<UserModel> getStudentById(UUID id) {
        return userRepository.findById(id);
    }
    
     //update user
     public String updateUser(UUID id, UserModel updateUser) {
             return userRepository.findById(id)
                 .map(user -> {
                     user.setNames(updateUser.getNames());
                user.setEmail(updateUser.getEmail());
                user.setPhoneNumber(updateUser.getPhoneNumber());
                user.setRole(updateUser.getRole());
                userRepository.save(user);
                return "User updated successfully";
            })
            .orElse("User not found");
    }
    //delete user
    public String deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User deleted successfully";
        } else {
            return "User not found";
        }
    }

     public boolean verifyOtp(String email, Integer otp) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return false;

        UserModel user = optionalUser.get();

        // Check OTP matches and not expired
        if (user.getOtp() != null &&
            user.getOtp().equals(otp) &&
            user.getOtpExpires() != null &&
            user.getOtpExpires().isAfter(LocalDateTime.now())) {

            user.setVerified(true);
            user.setOtp(null);
            user.setOtpExpires(null);

            userRepository.save(user);
            return true;
        }

        return false;
    }
}
