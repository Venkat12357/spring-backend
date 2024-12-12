package com.security.spring_security.Services;

import java.util.ArrayList;
import java.util.List;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.security.spring_security.Models.User;
import com.security.spring_security.Repositiries.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTservice jwt;

    @Autowired
    private MessageHelper messageHelper;
   
    public void registerUser(String username , String password)
    {
        int generateId = (int) (Math.random() * 10000);

        User user = new User();

        user.setId(generateId);
        user.setUserName(username);
        user.setPassword(password);
        user.setEmergencyContacts(new ArrayList<>());

        userRepo.save(user);
    }

    public String verify(User user) {
        
        Authentication auth = 
        authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName() , user.getPassword()));
        
        User user1 = userRepo.findByUserName(user.getUserName());

        if(auth.isAuthenticated())
        {
            return "success" + " " + user1.getId();
        }

        return "fail";

    }

    public String verifyPhone(String mail)
    {
        int otp = (int) (Math.random() * 10000);

        String message = "Your OTP to Login is :- " + otp;

        try {
            return messageHelper.sendEmergencyEmail(message , mail , otp);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAll() {

        return userRepo.findAll();
    }

    public User getUser(int id)
    {
       return userRepo.findById(id).orElse(new User());
    }
}
