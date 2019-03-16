package com.inz.demo.controller;

import com.inz.demo.domain.User;
import com.inz.demo.service.IUserService;
import com.inz.demo.service.impl.UserServiceImpl;
import com.inz.demo.util.DTOs.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final IUserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/users/add")
    public ResponseEntity create(@RequestBody UserDTO form) {
        if (userService.findUserByLogin(form.getLogin()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            userService.createUser(form);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (userService.findUserById(id).isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            userService.updateUser(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping(value = "/user/show/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable("id") Long id) {
        if (!userService.findUserById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/users")
    public ResponseEntity getUsers() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
}
