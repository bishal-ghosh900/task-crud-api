package com.example.task_crud_api.service;

import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.exception.UserAuthenticationException;
import com.example.task_crud_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        registeredUser.setTasks(null);
        return user;
    }

    // we don't need login, but this is just for learning purpose
    public User loginUser(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(token);
        if(!authentication.isAuthenticated()) {
            throw new UserAuthenticationException("username/password not matched", HttpStatus.UNAUTHORIZED);
        }
        User dbUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        dbUser.setTasks(null);
        return dbUser;
    }
}
