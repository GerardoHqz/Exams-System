package com.backend.exams.controllers;

import com.backend.exams.model.dtos.LoginDTO;
import com.backend.exams.model.dtos.TokenDTO;
import com.backend.exams.model.dtos.UserDTO;
import com.backend.exams.model.entities.Token;
import com.backend.exams.model.entities.Users;
import com.backend.exams.repositories.UserRepository;
import com.backend.exams.services.UserService;
import com.backend.exams.utils.RequestErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestErrorHandler errorHandler;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO info, BindingResult validations) throws Exception{

        if(validations.hasErrors()){
            return ResponseEntity.badRequest().body(errorHandler.mapErrors(validations.getFieldErrors()));
        }

        Users user = userRepository.findByUsername(info.getUsernmae());
        if(user == null){
            return ResponseEntity.badRequest().body("The user does not exist");
        }

        try{
            userService.login(info);
            Token token = userService.registerToken(user);
            return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
        }catch(Exception e){
            if(e.getMessage().equals("Invalid credentials")){
                return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
            }
            else if (e.getMessage().equals("User inactive")){
                return new ResponseEntity<>("The user is not active", HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO info, BindingResult validations) throws Exception {

        if (validations.hasErrors()) {
            return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        List<Users> users = userRepository.findAll();
        List<String> usernames = users.stream().map(Users::getUsername).toList();
        if (usernames.contains(info.getUsername())) {
            return new ResponseEntity<>("The user is already exist", HttpStatus.BAD_REQUEST);
        }

        try{
            userService.saveUser(info);
            return new ResponseEntity<>("User saved", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() throws Exception{
        Users user = userService.findUserAuthenticated();

        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        try{
            userService.toggleToken(user);
            return new ResponseEntity<>("User logged out", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
