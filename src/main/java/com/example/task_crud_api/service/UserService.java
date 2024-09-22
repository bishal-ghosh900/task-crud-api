package com.example.task_crud_api.service;

import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.exception.UserNotFoundException;
import com.example.task_crud_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException("invalid user id", HttpStatus.BAD_REQUEST);
        }
        user.get().setTasks(null);
        return user.get();
    }

    public User findUserWithTasksByUserId(
            int pageNumber,
            int maxCount
    ) {
        int userId = getCurrentUserId();
        User optionalUser = findUserById(userId);
        User user = userRepository.findUserWithTasksByUserId(userId, pageNumber, maxCount);
        return Objects.requireNonNullElseGet(user, () -> {
            optionalUser.setTasks(List.of());
            return optionalUser;
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserNotFoundException("Username not found", HttpStatus.BAD_REQUEST);
        }
        return user.get();
    }

    public int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
