package com.example.gymTracker.config;


import com.example.gymTracker.dto.request.LoginRequestDTO;
import com.example.gymTracker.dto.request.RegisterRequestDTO;
import com.example.gymTracker.dto.response.LoginResponseDTO;
import com.example.gymTracker.dto.response.RegisterResponseDTO;
import com.example.gymTracker.model.User;
import com.example.gymTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<RegisterResponseDTO> registerUser(RegisterRequestDTO registerRequestDTO){
        User newUser = new User();
        newUser.setBodyWeight(registerRequestDTO.bodyWeight());
        newUser.setEmail(registerRequestDTO.email());
        String passwordHash = passwordEncoder.encode(registerRequestDTO.password());
        newUser.setPassword(passwordHash);
        newUser.setGender(registerRequestDTO.gender());
        newUser.setName(registerRequestDTO.name());
        User userSaved = userRepository.save(newUser);

        RegisterResponseDTO responseDTO = new RegisterResponseDTO(userSaved.getUserId(),userSaved.getName(),userSaved.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO){
        //envelopa email com senha
        var credencials = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(),loginRequestDTO.password());

        var auth = this.authenticationManager.authenticate(credencials);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }




}
