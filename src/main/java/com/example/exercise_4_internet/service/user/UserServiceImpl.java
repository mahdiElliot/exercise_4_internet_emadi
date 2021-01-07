package com.example.exercise_4_internet.service.user;

import com.example.exercise_4_internet.model.user.User;
import com.example.exercise_4_internet.repositories.UserRepository;
import com.example.exercise_4_internet.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findByUserName(String username) {
        if (username == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is null");
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Long id) {
        if (id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null");
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
    public User deleteByUserName(String username) {
        User user = findByUserName(username);
        if (user == null) return null;
        userRepository.deleteByUsername(username);
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder
                .encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = findByUserName(username);
        if (user == null) return null;
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        user.setToken(token);
        return user;
    }

    @Override
    public User singUp(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "username already in use");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        user.setToken(token);
        user = userRepository.save(user);
        return user;
    }

    public User search(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User whoami(HttpServletRequest req) {
        return userRepository
                .findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))).orElse(null);
    }
}
