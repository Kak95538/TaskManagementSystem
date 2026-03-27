package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO dto) {
        User registeredUser = userService.register(dto); // ✅ pass DTO directly
        Map<String, Object> response = new HashMap<>();
        response.put("username", registeredUser.getUsername());
        response.put("role", registeredUser.getRole());

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO dto) {
        User existing = userService.findByUsername(dto.getUsername());
        if(existing != null && passwordEncoder.matches(dto.getPassword(), existing.getPassword())) {
            String token = jwtUtil.generateToken(existing.getUsername(), existing.getRole().name());
            // Wrap token in JSON
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", existing.getUsername());
            response.put("role", existing.getRole().name());
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
