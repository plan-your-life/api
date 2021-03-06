package com.planyourlifeapp.api.controllers;


import com.planyourlifeapp.api.models.User;
import com.planyourlifeapp.api.models.VerifyUser;
import com.planyourlifeapp.api.repositorys.UserRepository;
import com.planyourlifeapp.api.repositorys.VerifyUserRepository;
import com.planyourlifeapp.api.services.EmailServiceImpl;
import com.planyourlifeapp.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
public class RegisterController {

    private UserRepository userRepository;
    private VerifyUserRepository verifyUserRepository;
    private UserService userService;
    private EmailServiceImpl emailService;

    @Autowired
    public RegisterController(UserRepository userRepository, VerifyUserRepository verifyUserRepository, UserService userService, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.verifyUserRepository = verifyUserRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> login(@RequestBody User user) {
        //verification of userdata
        if (user.getUsername() == null || user.getPassword() == null || user.getPassword().length() < 8 || user.getEmail() == null) {
            return new ResponseEntity<>("Bad username or password", HttpStatus.BAD_REQUEST);
        }
        //check if the user already exists
        if (userRepository.getByUsername(user.getUsername()) != null || userRepository.getByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("exists", HttpStatus.CONFLICT);
        }
        //todo: check if email already exists or username
        userService.createUser(user);
        emailService.sendVerifyEmail(user.getEmail(), user.getVerify().getVerifyKey(), user.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "key") String key) {
        VerifyUser fullKey = verifyUserRepository.getByVerifyKey(key);
        if (fullKey == null) {
            return new ResponseEntity<>("Key not valid", HttpStatus.BAD_REQUEST);
        }
        User user = fullKey.getUser();
        //Check if the user or key exists
        if (user == null) {
            return new ResponseEntity<>("Key not valid", HttpStatus.BAD_REQUEST);
        }
        user.setActive(true);
        verifyUserRepository.deleteById(user.getVerify().getId());
        user.setVerify(null);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
