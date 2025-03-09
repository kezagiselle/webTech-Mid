package auca.ac.rw.cinemaTicket.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.cinemaTicket.models.UserModel;
import auca.ac.rw.cinemaTicket.services.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserServices userService;

    @PostMapping(value = "/saveUser", consumes = "application/json")
    public ResponseEntity<String> saveUser(@RequestBody UserModel user) {
        String savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

     @GetMapping(value = "/all")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        System.out.println("Fetching all students...");
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        Optional<UserModel> user = userService.getStudentById(id);
        return user.map(value -> ResponseEntity.ok((Object) value)) 
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found"));
    }

     @PutMapping(value = "/updates/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserModel updatedUser) {
        String updateMessage = userService.updateUser(id, updatedUser);
        if (updateMessage.equalsIgnoreCase("User updated successfully")) {
            return ResponseEntity.ok(updateMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateMessage);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        String deleteMessage = userService.deleteUser(id);
        if (deleteMessage.equalsIgnoreCase("User deleted successfully")) {
            return ResponseEntity.ok(deleteMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deleteMessage);
        }
    }
}
