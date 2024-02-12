package com.backend.exams.services.serviceImpl;

import com.backend.exams.model.dtos.LoginDTO;
import com.backend.exams.model.dtos.UserDTO;
import com.backend.exams.model.entities.RolexUser;
import com.backend.exams.model.entities.Token;
import com.backend.exams.model.entities.Users;
import com.backend.exams.repositories.RoleRepository;
import com.backend.exams.repositories.RolexUserRepository;
import com.backend.exams.repositories.TokenRepository;
import com.backend.exams.repositories.UserRepository;
import com.backend.exams.services.UserService;
import com.backend.exams.utils.JWTTools;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolexUserRepository rolexUserRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JWTTools jwtTools;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveUser(UserDTO info) throws Exception {
        try {
            Users user = new Users(info.getUsername(), passwordEncoder.encode(info.getPassword()), info.getName(), info.getLastname(),
                    info.getEmail(), info.getTelephone(), true, info.getProfile());
            boolean isValid = userRepository.existsByUsername(user.getUsername());
            if (isValid) {
                throw new Exception("The user is already exist");
            } else {
                userRepository.save(user);
                for (String role : info.getRolesUser()) {
                    if (!roleRepository.existsByName(role)) {
                        throw new Exception("The role does not exist");
                    }
                    RolexUser roleUser = new RolexUser(user, roleRepository.findByName(role));
                    rolexUserRepository.save(roleUser);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void toggleUserStatus(String username) {
        Users user = userRepository.findByUsername(username);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token registerToken(Users user) throws Exception {
        cleanTokens(user);

        String tokenString = jwtTools.generateToken(user);
        Token token = new Token(tokenString, user);
        tokenRepository.save(token);

        return token;
    }

    @Override
    public Boolean isTokenValid(Users user, String token) {
        try {
            cleanTokens(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

            tokens.stream().filter(t -> t.getContent().equals(token)).findAny().orElseThrow(() -> new Exception("Token not found"));
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanTokens(Users user) throws Exception {
        List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

        tokens.forEach(t -> {
            if (!jwtTools.verifyToken(t.getContent())) {
                t.setActive(false);
                tokenRepository.save(t);
            }
        });

    }

    @Override
    public Users getUserFromToken(String info) {
        List<Token> token = tokenRepository.findAll().stream()
                .filter(t -> t.getContent().matches(info) && t.getActive().equals(true)).collect(Collectors.toList());

        Users user = token.get(0).getUser();

        return user;

    }

    @Override
    public Boolean comparePass(String toCompare, String current) {
        return passwordEncoder.matches(toCompare, current);
    }

    @Override
    public void toggleToken(Users user) {
        List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

        if (!tokens.isEmpty()) {
            tokens.forEach(t -> {
                t.setActive(false);
                tokenRepository.save(t);
            });
        }

    }

    @Override
    public Users findUserAuthenticated() {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username);
    }

    @Override
    public void login(LoginDTO info) throws Exception {
        Users user = userRepository.findByUsername(info.getUsername());

        if(!comparePass(info.getPassword(), user.getPassword())){
            throw new Exception("Invalid credentials");
        }

        if (!user.isEnabled()) {
            throw new Exception("User inactive");
        }
    }
}
