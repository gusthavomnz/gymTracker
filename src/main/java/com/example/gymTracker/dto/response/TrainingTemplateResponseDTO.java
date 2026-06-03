package com.example.gymTracker.dto.response;

import java.util.List;

public record TrainingTemplateResponseDTO(Long templateId, String name, List<Long> exerciseIds) {}
