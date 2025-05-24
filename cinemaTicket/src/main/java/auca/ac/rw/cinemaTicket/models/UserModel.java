package auca.ac.rw.cinemaTicket.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "names", nullable = false)
    private String names;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    private String role;
  
    @Column(name = "password",  nullable = false)
    private String password;

    @Column(name = "otp", nullable = true)
    private Integer otp;
 
     @Column(name = "otp_expires")
    private LocalDateTime otpExpires;

    @Column(name ="verified", nullable = false)
    private Boolean verified = false;

    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private List<BookingModel> bookings;

    

    public UserModel() {}

    public UserModel(UUID id, String names, String email, String phoneNumber, String role, String password,
                     Integer otp, LocalDateTime otpExpires, Boolean verified, List<BookingModel> bookings) {
        this.id = id;
        this.names = names;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;
        this.otp = otp;
        this.otpExpires = otpExpires;
        this.verified = verified;
        this.bookings = bookings;
    }

    // ✅ Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpExpires() {
        return otpExpires;
    }

    public void setOtpExpires(LocalDateTime otpExpires) {
        this.otpExpires = otpExpires;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public List<BookingModel> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingModel> bookings) {
        this.bookings = bookings;
    }

    public boolean isEnabled() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
    }

    public boolean isLocked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isLocked'");
    }

    public boolean isActive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isActive'");
    }
}
