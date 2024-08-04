package com.mako.authApp.controller;

import com.mako.authApp.entities.User;
import com.mako.authApp.repository.UserRepository;
import com.mako.authApp.service.AuthService;
import com.mako.authApp.service.EmailService;
import jakarta.mail.MessagingException;
import org.hibernate.query.sqm.AliasCollisionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.UUID;

@RestController
public class AuthenticationController {

    @Autowired
    EmailService emailService;

    @Autowired
    AuthService authService;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/signup")
    public ModelAndView signUp(@RequestBody User user) throws MessagingException, NoSuchAlgorithmException {
        User existingUser = userRepository.findByUserEmail(user.getUserEmail());
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with this email already exists.");
        }
        user.setPassword(authService.hashPassword(user.getPassword()));
        // Generate activation token
        String token = UUID.randomUUID().toString();
        user.setActivationToken(token);
        user.setActive(false);
        // Set token expiry date (24 hours from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        user.setActivationTokenExpiry(calendar.getTime());

        // Save the user
        userRepository.save(user);

        // Build the activation link
        String activationLink = "http://127.0.0.1:8080/activate?token=" + token;

        // Build the email content
        String emailContent = "<html>" +
                "<body style='font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0;'>" +
                "<div style='background-color: #e9eff1; padding: 20px;'>" +
                "<div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; padding: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>" +
                "<h2 style='color: #2c3e50; text-align: center;'>Welcome to MakTech!</h2>" +
                "<p style='font-size: 16px; color: #34495e;'>Hello " + user.getUserNames() + ",</p>" +
                "<p style='font-size: 16px; color: #34495e;'>Thank you for registering with us. To complete your account setup, please activate your account by clicking the button below:</p>" +
                "<p style='text-align: center;'><a href='" + activationLink + "' style='display: inline-block; padding: 12px 20px; font-size: 16px; color: #ffffff; background-color: #3498db; text-decoration: none; border-radius: 5px; font-weight: bold;'>Activate Your Account</a></p>" +
                "<p style='font-size: 16px; color: #34495e;'>Once activated, you can log in using your login credentials:</p>" +
                "<p style='font-size: 16px; color: #34495e;'>Please keep this information safe and do not share it with anyone.</p>" +
                "<p style='font-size: 16px; color: #34495e;'>If you have any questions, feel free to reach out to us.</p>" +
                "<p style='font-size: 16px; color: #34495e;'>Thank you,<br/>The MakTech Team</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        emailService.sendStyledEmail(
                user.getUserEmail(),
                "MakTech Account Creation",
                user.getUserNames(),
                emailContent
        );


        return new ModelAndView("activationLinkResent.html");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user1) throws NoSuchAlgorithmException {
        User user = userRepository.findByUserEmail(user1.getUserEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: User not found");
        }

        if (!user.getActive()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: User is not active");
        }

        if (user.getPassword().equals(authService.hashPassword(user1.getPassword()))) {
            // Handle successful login (e.g., generate session or token)
            return ResponseEntity.ok("Login successful!");
        } else {
            // Handle login failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Incorrect password");
        }
    }

}
