package com.example.gymTracker.controller;

import com.example.gymTracker.dto.request.ExerciseRequestDTO;
import com.example.gymTracker.dto.response.ExerciseResponseDTO;
import com.example.gymTracker.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDTO>> listAll() {
        return ResponseEntity.ok(exerciseService.findAll());
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<ExerciseResponseDTO>> listByGroupId(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.findByTrainingGroup(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseResponseDTO> createExercise(@RequestBody @Valid ExerciseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.createExercise(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> updateExercise(@PathVariable Long id, @RequestBody @Valid ExerciseRequestDTO dto) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ExerciseResponseDTO>> searchByName(@RequestParam String q) {
        return ResponseEntity.ok(exerciseService.findByName(q));
    }

    @GetMapping("/muscle-group/name/{name}")
    public ResponseEntity<List<ExerciseResponseDTO>> searchByMuscleGroup(@PathVariable String name) {
        return ResponseEntity.ok(exerciseService.findByMuscleGroupName(name));
    }
}
