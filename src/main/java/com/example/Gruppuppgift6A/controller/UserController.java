package com.example.Gruppuppgift6A.controller;

import com.example.Gruppuppgift6A.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserCredentials request) {

        if (request.username == null || request.password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }

        UserService.UserResponse response = userService.login(request);
        return ResponseEntity.ok(response.token());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UserCredentials request) {
        if (request.username == null || request.password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing parameters");
        }
        UserService.UserResponse response = userService.register(request);
        return ResponseEntity.ok(response.username() + " created successfully!");
    }

    public record UserCredentials(String username, String password) {}
}
