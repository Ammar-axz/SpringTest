package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){

        return authService.registerUser(user);
    }
    
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("code") String code){
        return authService.verifyUser(code);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user){

        return authService.loginUser(user);
    }
}