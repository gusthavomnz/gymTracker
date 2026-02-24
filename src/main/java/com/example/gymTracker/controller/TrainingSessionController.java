package com.example.gymTracker.controller;

import com.example.gymTracker.dto.TrainingSessionDTO;
import com.example.gymTracker.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ts")
public class TrainingSessionController {

    @Autowired
    TrainingSessionService trainingSessionService;


    @PostMapping("/createTS")
    public ResponseEntity createTrainingSession(@RequestBody TrainingSessionDTO trainingSessionDTO){
        TrainingSessionDTO savedSession = trainingSessionService.createTrainingSession(trainingSessionDTO);
        return ResponseEntity.ok(savedSession);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDTO> getReport(@PathVariable Long id) {
        TrainingSessionDTO report = trainingSessionService.getReportSession(id);
        return ResponseEntity.ok(report);
    }
}
