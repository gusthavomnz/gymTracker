package com.example.gymTracker.controller;


import com.example.gymTracker.config.AuthService;
import com.example.gymTracker.dto.request.LoginRequestDTO;
import com.example.gymTracker.dto.request.RegisterRequestDTO;
import com.example.gymTracker.dto.response.LoginResponseDTO;
import com.example.gymTracker.dto.response.RegisterResponseDTO;
import com.example.gymTracker.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.gymTracker.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        var response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        var response = authService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
    }



}
