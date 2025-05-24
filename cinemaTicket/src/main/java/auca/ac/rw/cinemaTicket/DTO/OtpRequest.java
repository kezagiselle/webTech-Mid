package auca.ac.rw.cinemaTicket.DTO;

public class OtpRequest {
    private String email;
    private Integer otp;

    public OtpRequest() {
    }

    public OtpRequest(String email, Integer otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
