package com.example.gymTracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TrainingGroupDTO {

    private long id;

    @NotBlank(message = "O nome do grupo muscular é obrigatório")
    private String name;

    public TrainingGroupDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
