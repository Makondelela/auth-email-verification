package com.mako.authApp.service;

import com.mako.authApp.entities.User;
import com.mako.authApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean activateUserByToken(String token) {
        User user = userRepository.findByActivationToken(token);
        if (user != null && !user.getActive() && user.getActivationTokenExpiry().after(new Date())) {
            user.setActive(true);
            user.setActivationToken(null); // Optional: Remove token after activation
            user.setActivationTokenExpiry(null); // Optional: Remove expiry date
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
