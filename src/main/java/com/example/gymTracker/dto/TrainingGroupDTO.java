package com.example.gymTracker.dto;

import lombok.Data;

@Data
public class TrainingGroupDTO {

    private long id;
    private String name;

    public TrainingGroupDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
