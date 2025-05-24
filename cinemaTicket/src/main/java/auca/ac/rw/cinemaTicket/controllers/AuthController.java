package auca.ac.rw.cinemaTicket.controllers;

import auca.ac.rw.cinemaTicket.DTO.OtpRequest;
import auca.ac.rw.cinemaTicket.DTO.OtpRequest;

// import auca.ac.rw.cinemaTicket.DTO.LoginRequest;
import auca.ac.rw.cinemaTicket.Security.JwtUtil;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;
import auca.ac.rw.cinemaTicket.services.UserServices;

// import java.util.HashMap;
// import java.util.Map;
import java.util.Optional;

// import javax.naming.AuthenticationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private JwtUtil jwtUtil ;


    public AuthController(AuthenticationManager authenticationManager, 
                         JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody OtpRequest request) {
    try {
        // 1. Find user by email
        Optional<UserModel> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or OTP");
        }

        UserModel user = userOptional.get();

        // 2. Check if OTP matches
       if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or OTP");
}

        // 3. Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // 4. Return AuthResponse (make sure AuthResponse has public modifier)
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));

   } catch (Exception e) {
    e.printStackTrace(); // Add this line to see the real error in logs
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
}
}



    // Inner class for response
    private record AuthResponse(String token, String type) {}



    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {
        boolean verified = userServices.verifyOtp(request.getEmail(), request.getOtp());
        if (verified) {
            return ResponseEntity.ok("OTP Verified Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserModel user) {
        try {
            userServices.signUpUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully. OTP sent to email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during registration: " + e.getMessage());
        }
    }
}
