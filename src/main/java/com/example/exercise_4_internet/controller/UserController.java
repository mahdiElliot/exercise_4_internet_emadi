package com.example.exercise_4_internet.controller;

import com.example.exercise_4_internet.model.user.User;
import com.example.exercise_4_internet.model.user.UserDTO;
import com.example.exercise_4_internet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO.getUsername(), userDTO.getPassword());
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user.convertToDTO());
    }

    @PutMapping(path = "user/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        userService.save(userDTO.convertToEntity());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.singUp(userDTO.convertToEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(user.convertToDTO());
    }
}
