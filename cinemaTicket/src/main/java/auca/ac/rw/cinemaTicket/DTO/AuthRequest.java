package auca.ac.rw.cinemaTicket.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "OTP is required")
    private String otp;

    // Constructors
    public AuthRequest() {}

    public AuthRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Object getPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }
}
