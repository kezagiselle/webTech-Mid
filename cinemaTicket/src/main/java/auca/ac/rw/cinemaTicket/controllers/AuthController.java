package auca.ac.rw.cinemaTicket.controllers;

import auca.ac.rw.cinemaTicket.DTO.AuthRequest;
import auca.ac.rw.cinemaTicket.DTO.LoginRequest;
import auca.ac.rw.cinemaTicket.DTO.OtpRequest;
import auca.ac.rw.cinemaTicket.Security.JwtUtil;
import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;
import auca.ac.rw.cinemaTicket.services.UserServices;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            String token = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok().body(new AuthResponse(token, "Bearer"));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
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
