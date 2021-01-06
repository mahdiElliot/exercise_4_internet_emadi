package com.example.exercise_4_internet.controller;

import com.example.exercise_4_internet.model.User;
import com.example.exercise_4_internet.model.UserDTO;
import com.example.exercise_4_internet.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        userDTO.setToken("token");
        User user = userService.save(userDTO.convertToEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(user.convertToDTO());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll().stream().map(User::convertToDTO).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user.convertToDTO());
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String username) {
        User user = userService.findByUserName(username);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        return ResponseEntity.ok(user.convertToDTO());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        userService.save(userDTO.convertToEntity());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
        User user = userService.delete(id);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        return ResponseEntity.ok(user.convertToDTO());
    }
}
