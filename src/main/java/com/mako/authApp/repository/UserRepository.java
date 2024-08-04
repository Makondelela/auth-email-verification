package com.mako.authApp.repository;


import com.mako.authApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserEmail(String email);

    User findByActivationToken(String token);
}

