package com.example.exercise_4_internet.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Email
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    public User() {
    }

    public User(Long id, String username, String password, String email, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public UserDTO convertToDTO() {
        return new UserDTO(id, username, password, email, token);
    }
}
