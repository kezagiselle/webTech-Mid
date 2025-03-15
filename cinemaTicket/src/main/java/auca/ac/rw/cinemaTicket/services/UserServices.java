package auca.ac.rw.cinemaTicket.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.repositories.UserRepository;

@Service
public class UserServices {
   
    @Autowired
     public UserRepository userRepository;

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
}
