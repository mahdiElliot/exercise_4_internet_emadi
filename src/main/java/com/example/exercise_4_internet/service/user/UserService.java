package com.example.exercise_4_internet.service.user;

import com.example.exercise_4_internet.model.user.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findByUserName(String username);

    User findById(Long id);

    User delete(Long id);

    User deleteByUserName(String username);

    User save(User user);

    User login(String username, String password);

    User singUp(User user);

}
