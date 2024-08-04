package com.mako.authApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UIController {
    @GetMapping("/signUp.html")
    public ModelAndView signUp(){
        return new ModelAndView("signup.html");
    }

    @GetMapping("/index.html")
    public ModelAndView index(){
        return new ModelAndView("signup.html");
    }

    @GetMapping("/signIn.html")
    public ModelAndView signIn(){
        return new ModelAndView("signIn.html");
    }

    @GetMapping("/home.html")
    public ModelAndView home(){
        return new ModelAndView("home.html");
    }

}
