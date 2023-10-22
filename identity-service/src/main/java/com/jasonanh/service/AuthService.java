package com.jasonanh.service;

import com.jasonanh.entity.UserCredential;
import com.jasonanh.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    public String saveUser(UserCredential credential){
        if(repository.findUserCredentialByName(credential.getName()) != null){
            return "Username already in the database";
        }
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "added to the system";
    }
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public UserCredential login(String username){
        UserCredential response = repository.findUserCredentialByName(username);
        response.setToken(generateToken(username));
        return response;

    }


}
