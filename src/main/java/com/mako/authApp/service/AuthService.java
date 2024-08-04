package com.mako.authApp.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AuthService {

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest  messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for(byte bit:hash){
            stringBuilder.append(Integer.toString((bit&0xff)+0x100,16).substring(1));
        }
        return stringBuilder.toString();
    }
}
