package com.example.gymTracker.controller;


import com.example.gymTracker.dto.ExerciseSetDTO;
import com.example.gymTracker.service.ExerciseSetService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ExerciseSetDTO> createSet(@RequestBody ExerciseSetDTO dto) {
        return exerciseSetService.saveSet(dto);
    }
}
