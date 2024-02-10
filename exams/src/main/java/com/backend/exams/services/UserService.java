package com.backend.exams.services;

import com.backend.exams.model.dtos.LoginDTO;
import com.backend.exams.model.dtos.UserDTO;
import com.backend.exams.model.entities.Token;
import com.backend.exams.model.entities.Users;

public interface UserService {
    public void saveUser(UserDTO user) throws Exception;
    public Users findByUsername(String username);
    public void toggleUserStatus(String username);
    //Token management
    public Token registerToken(Users user) throws Exception;
    public Boolean isTokenValid(Users user, String token);
    public void cleanTokens(Users user) throws Exception;
    public Users getUserFromToken (String info);
    public Boolean comparePass(String toCompare, String current);
    public void toggleToken(Users user);
    public Users findUserAuthenticated();
    public void login(LoginDTO info) throws Exception;
}
