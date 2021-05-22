package com.sda.store.service.implementation;

import com.sda.store.excpetion.ResourceAlreadyPresentInDatabase;
import com.sda.store.model.User;
import com.sda.store.repository.UserRepository;
import com.sda.store.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementantion implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    public UserServiceImplementantion(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public User create(User user) {
        User userInDatabase = userRepository.findByEmail(user.getEmail());
        if (userInDatabase != null){
            throw new ResourceAlreadyPresentInDatabase(String.format("User with email %s already exist", user.getEmail()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
