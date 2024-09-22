package com.example.task_crud_api.service;

import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.exception.UserNotFoundException;
import com.example.task_crud_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User findUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException("invalid user id", HttpStatus.BAD_REQUEST);
        }
        return user.get();
    }

    public User findUserWithTasksByUsername(
            int userId,
            int pageNumber,
            int maxCount
    ) {
        User optionalUser = findUserById(userId);
        User user = userRepository.findUserWithTasksByUsername(userId, pageNumber, maxCount);
        if(user == null) {
            user = optionalUser;
            optionalUser.setTasks(List.of());
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserNotFoundException("Username not found", HttpStatus.BAD_REQUEST);
        }
        return user.get();
    }
}
