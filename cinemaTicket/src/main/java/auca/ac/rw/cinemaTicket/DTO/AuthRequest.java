package auca.ac.rw.cinemaTicket.DTO;

public class AuthRequest {

    private String email;
    private String password;
    public String getUsername() {
        return email;
    }
    public void setUsername(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Object getEmail() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
    }
    
    
}

