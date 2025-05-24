package auca.ac.rw.cinemaTicket.controllers;

import auca.ac.rw.cinemaTicket.DTO.OtpRequest;
import auca.ac.rw.cinemaTicket.DTO.OtpRequest;

// import auca.ac.rw.cinemaTicket.DTO.LoginRequest;
import auca.ac.rw.cinemaTicket.Security.JwtUtil;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;
import auca.ac.rw.cinemaTicket.services.UserServices;

import java.time.LocalDateTime;
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

        // Debug: Print OTP and expiry
        System.out.println("[DEBUG] Received OTP: " + request.getOtp());
        System.out.println("[DEBUG] Stored OTP: " + user.getOtp() + ", Expires: " + user.getOtpExpires());
        System.out.println("[DEBUG] Current Time: " + LocalDateTime.now());

        // 2. Check OTP expiry first
        if (user.getOtpExpires() != null && user.getOtpExpires().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP has expired");
        }

        // 3. Compare OTPs (handle type mismatch)
        if (user.getOtp() == null || !user.getOtp().toString().equals(request.getOtp().toString())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }

        // 4. Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // 5. (Optional) Clear OTP after successful login
        user.setOtp(null);
        user.setOtpExpires(null);
        userRepository.save(user);

        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
    }
}

// Static response class
public record AuthResponse(String token, String type) {}

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
