package auca.ac.rw.cinemaTicket.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


// import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;

@Service
public class UserServices {
   
    @Autowired
     public UserRepository userRepository;

     @Autowired
    private PasswordEncoder passwordEncoder;
    
     @Autowired
    private EmailService emailService;

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
    Optional<UserModel> userOptional = userRepository.findByEmail(email);
    if (userOptional.isEmpty()) {
        return false;
    }

    UserModel user = userOptional.get();
    
    // Check OTP match and expiry
    if (user.getOtp() == null || !user.getOtp().equals(otp)) {
        return false;
    }
    if (user.getOtpExpires().isBefore(LocalDateTime.now())) {
        return false;
    }

    // **Don’t clear OTP yet** (keep it for login)
    return true;
}

    public UserModel signUpUser(UserModel user) {
    // Check if user already exists by email
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("User with this email already exists");
    }

    // Generate 6-digit OTP
    String otp = String.format("%06d", new Random().nextInt(999999));

    // Set OTP and expiration (10 minutes from now)
    user.setOtp(Integer.parseInt(otp));
    user.setOtpExpires(LocalDateTime.now().plusMinutes(10));
    user.setVerified(false);

    // Encode the password before saving
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Save user
    UserModel savedUser = userRepository.save(user);

    // Try sending OTP email
    try {
        emailService.sendOtpEmail(savedUser.getEmail(), otp);
    } catch (Exception e) {
        e.printStackTrace(); // log the real error
        throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
    }

    return savedUser;
}


}

