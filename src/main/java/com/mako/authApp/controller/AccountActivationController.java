package com.mako.authApp.controller;

import com.mako.authApp.entities.User;
import com.mako.authApp.repository.UserRepository;
import com.mako.authApp.service.EmailService;
import com.mako.authApp.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.UUID;

@RestController
public class AccountActivationController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;


    @GetMapping("/activate")
    public ModelAndView activateAccount(@RequestParam("token") String token) {
        boolean isActivated = userService.activateUserByToken(token);
        if (isActivated) {
            return new ModelAndView("activationSuccess");
        } else {
            return new ModelAndView("activationFailure");
        }
    }

    @PostMapping("/request-new-activation")
    public ModelAndView requestNewActivation(@RequestParam("email") String email) throws MessagingException {
        User user = userRepository.findByUserEmail(email);

        if (user != null && !user.getActive()) {
            String token = UUID.randomUUID().toString();
            user.setActivationToken(token);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, 24);
            user.setActivationTokenExpiry(calendar.getTime());

            userRepository.save(user);

            String activationLink = "http://127.0.0.1:8080/activate?token=" + token;

            String emailContent = "<html>" +
                    "<body style='font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0;'>" +
                    "<div style='background-color: #f4f7f9; padding: 20px;'>" +
                    "<div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 10px; padding: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>" +
                    "<h2 style='color: #2c3e50; text-align: center;'>Welcome to Mako!</h2>" +
                    "<p style='font-size: 16px; color: #34495e;'>Hello " + user.getUserNames() + ",</p>" +
                    "<p style='font-size: 16px; color: #34495e;'>Thank you for joining us. To complete your registration, please activate your account by clicking the button below:</p>" +
                    "<p style='text-align: center;'><a href='" + activationLink + "' style='display: inline-block; padding: 12px 20px; font-size: 16px; color: #ffffff; background-color: #3498db; text-decoration: none; border-radius: 5px; font-weight: bold;'>Activate Your Account</a></p>" +
                    "<p style='font-size: 16px; color: #34495e;'>Once activated, you can log in using your login credentials:</p>" +
                    "<p style='font-size: 16px; color: #34495e;'>Please keep your credentials safe and do not share them with anyone.</p>" +
                    "<p style='font-size: 16px; color: #34495e;'>If you have any questions, feel free to reach out to us.</p>" +
                    "<p style='font-size: 16px; color: #34495e;'>Thank you,<br/>The Mako Team</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            emailService.sendStyledEmail(
                    user.getUserEmail(),
                    "Welcome to Mako - Activate Your Account",
                    user.getUserNames(),
                    emailContent
            );

            return new ModelAndView("activationLinkResent.html");
        } else if (user.getActive()) {
            return new ModelAndView("accountAlreadyActive");
        } else {
            return new ModelAndView("activationFailure");
        }
    }
}
