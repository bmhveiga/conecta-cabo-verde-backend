package com.conectacaboverde.user.controller;

import com.conectacaboverde.user.dto.LoginRequestDTO;
import com.conectacaboverde.user.dto.LoginResponseDTO;
import com.conectacaboverde.user.dto.UserRequestDTO;
import com.conectacaboverde.user.dto.UserResponseDTO;
import com.conectacaboverde.user.entity.User;
import com.conectacaboverde.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        return userService.createUser(requestDTO);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO requestDTO) {
        return userService.login(requestDTO);
    }
}