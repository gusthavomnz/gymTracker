package com.example.gymTracker.controller;

import com.example.gymTracker.dto.request.ExerciseSetRequestDTO;
import com.example.gymTracker.dto.response.ExerciseSetResponseDTO;
import com.example.gymTracker.service.ExerciseSetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise-sets")
public class ExerciseSetController {

    @Autowired
    private ExerciseSetService exerciseSetService;

    @PostMapping
    public ResponseEntity<ExerciseSetResponseDTO> createSet(@RequestBody @Valid ExerciseSetRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseSetService.saveSet(dto));
    }
}
