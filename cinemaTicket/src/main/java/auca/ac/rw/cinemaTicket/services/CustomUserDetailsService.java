package auca.ac.rw.cinemaTicket.services;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // In a real application, you would get this from your database
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // This is just a mock implementation
        // Replace with actual user lookup from your database
        if ("user@example.com".equals(email)) {
            return new User("user@example.com", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", 
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
