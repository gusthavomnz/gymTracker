package com.example.gymTracker.controller;


import com.example.gymTracker.dto.ExerciseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.gymTracker.service.ExerciseService;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {


    @Autowired
ExerciseService exerciseService;


@Transactional
@GetMapping("/list")
    public ResponseEntity<List<ExerciseDTO>> listAll(){
        return ResponseEntity.ok(exerciseService.findAll());
    }


    @GetMapping("/group/{id}")
    public ResponseEntity<List<ExerciseDTO>> listByGroupId(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.findByTrainingGroup(id));
    }


    @PostMapping
    public ResponseEntity<ExerciseDTO> createExercise(@RequestBody ExerciseDTO exerciseDTO){
     ExerciseDTO newExercise = exerciseService.createExercise(exerciseDTO);
     return ResponseEntity.status(HttpStatus.CREATED).body(newExercise);
    }



}
