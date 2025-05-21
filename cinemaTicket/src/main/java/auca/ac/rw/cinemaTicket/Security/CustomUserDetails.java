package auca.ac.rw.cinemaTicket.Security;

import auca.ac.rw.cinemaTicket.models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final UserModel user;

    public CustomUserDetails(UserModel user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole();
        if (role == null || role.isEmpty()) {
            return Collections.emptySet();
        }
        String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Ensure this is the encoded password
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Make sure your login uses email
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive(); // Add this field to UserModel
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked(); // Add this field to UserModel
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or add password expiry field to UserModel
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // Add this field to UserModel
    }

    public UserModel getUser() {
        return user;
    }
}