package com.example.gymTracker.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class TrainingSessionDTO {


    private String name;

    private long TsId;

    private LocalDate date;

    private String notes;

    private long userId;

    private long tgId;

    private List<ExerciseSetDTO> exerciseSets;

}
