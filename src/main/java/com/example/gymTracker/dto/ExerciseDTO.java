package com.example.gymTracker.dto;

import lombok.Data;

@Data
public class ExerciseDTO {

    private Long id;
    private String name;
    private Long trainingGroupId;


    public ExerciseDTO(Long id, String name, Long trainingGroupId) {
        this.id = id;
        this.name = name;
        this.trainingGroupId = trainingGroupId;
    }
}

