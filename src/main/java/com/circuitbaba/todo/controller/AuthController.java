package com.circuitbaba.todo.controller;

import com.circuitbaba.todo.dto.ResponseToken;
import com.circuitbaba.todo.security.JwtUtil;
import com.circuitbaba.todo.security.model.User;
import com.circuitbaba.todo.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin("http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(user.getRole().trim().toUpperCase());
        repo.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseToken> login(@RequestBody User user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        // Store authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate token from authenticated principal
        String token = jwtUtil.generateToken(authentication);
        return ResponseEntity.ok(ResponseToken.builder().token(token).build());
    }
}

