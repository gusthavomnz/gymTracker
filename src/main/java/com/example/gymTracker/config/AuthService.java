package com.example.gymTracker.config;


import com.example.gymTracker.dto.request.LoginRequestDTO;
import com.example.gymTracker.dto.request.RegisterRequestDTO;
import com.example.gymTracker.dto.response.LoginResponseDTO;
import com.example.gymTracker.dto.response.RegisterResponseDTO;
import com.example.gymTracker.model.User;
import com.example.gymTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
AuthenticationManager authenticationManager;

@Autowired
    PasswordEncoder passwordEncoder;
@Autowired
    TokenService tokenService;

    public User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        throw new RuntimeException("User not authenticated or invalid principal type");
    }


    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO){
        User newUser = new User();
        newUser.setBodyWeight(registerRequestDTO.bodyWeight());
        newUser.setEmail(registerRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        newUser.setGender(registerRequestDTO.gender());
        newUser.setName(registerRequestDTO.name());
        User userSaved = userRepository.save(newUser);

        return new RegisterResponseDTO(userSaved.getUserId(), userSaved.getName(), userSaved.getEmail());
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        var credentials = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        var auth = this.authenticationManager.authenticate(credentials);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }




}
