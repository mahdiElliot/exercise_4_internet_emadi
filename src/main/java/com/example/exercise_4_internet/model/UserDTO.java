package com.example.exercise_4_internet.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class UserDTO {
    private long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Email
    private String email;

    private String token;

    public UserDTO() {
        super();
    }

    public UserDTO(Long id, String username, String password, String email, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }

    public User convertToEntity() {
        return new User(id, username, password, email, token);
    }
}
