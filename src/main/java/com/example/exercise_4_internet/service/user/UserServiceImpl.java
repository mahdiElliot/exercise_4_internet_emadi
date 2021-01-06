package com.example.exercise_4_internet.service.user;

import com.example.exercise_4_internet.model.User;
import com.example.exercise_4_internet.model.UserDTO;
import com.example.exercise_4_internet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findByUserName(String username) {
        if (username == null) return null;
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Long id) {
        if (id == null) return null;
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User delete(Long id) {
        User user = findById(id);
        if (user == null) return null;
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
