package com.example.gymTracker.repository;

import com.example.gymTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Remova o Optional para o Spring Security e use o nome padrão
    UserDetails findByEmail(String email);
}
