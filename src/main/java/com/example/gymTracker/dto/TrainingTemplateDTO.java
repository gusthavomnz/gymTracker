package com.example.gymTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTemplateDTO {
    private Long templateId;
    private String name;
    private List<Long> exerciseIds;
}
