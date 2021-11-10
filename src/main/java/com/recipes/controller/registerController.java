package com.recipes.controller;

import com.recipes.dao.UserRepository;
import com.recipes.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class registerController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public Map<String, String> registerUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return Collections.singletonMap("useremail", user.getEmail());
    }
}
