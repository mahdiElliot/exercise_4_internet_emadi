package com.example.exercise_4_internet.service.user;

import com.example.exercise_4_internet.model.User;
import com.example.exercise_4_internet.model.UserDTO;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findByUserName(String username);

    User findById(Long id);

    User delete(Long id);

    User save(User user);

}
