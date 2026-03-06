package com.example.gymTracker.dto.request;


import com.example.gymTracker.model.Enum.GenderEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RegisterRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @Email(message = "Email inválido")
        @NotBlank(message = "O email é obrigatório")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String password,

        @NotNull(message = "O peso corporal é obrigatório")
        BigDecimal bodyWeight,

        @NotNull(message = "O gênero é obrigatório")
        GenderEnum gender
) {}
