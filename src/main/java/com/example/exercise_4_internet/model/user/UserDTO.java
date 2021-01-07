package com.example.exercise_4_internet.model.user;

import com.example.exercise_4_internet.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String email;

    List<Role> roles;

    private String token;

    public User convertToEntity() {
        return new User(id, username, password, email, roles, token);
    }
}
