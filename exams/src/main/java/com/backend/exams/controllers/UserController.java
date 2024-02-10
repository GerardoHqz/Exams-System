package com.backend.exams.controllers;

import com.backend.exams.model.dtos.UserDTO;
import com.backend.exams.model.entities.Users;
import com.backend.exams.repositories.UserRepository;
import com.backend.exams.services.UserService;
import com.backend.exams.utils.RequestErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestErrorHandler errorHandler;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> toggleUserStatus(@PathVariable String username) {
        Users user = userService.findUserAuthenticated();


        if (user == null) {
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }

        try{
            userService.toggleUserStatus(user.getUsername());
            return new ResponseEntity<>("User status updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
