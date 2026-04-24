package com.example.gymTracker.controller;

import com.example.gymTracker.dto.TrainingSessionDTO;
import com.example.gymTracker.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/training-sessions")
public class TrainingSessionController {

    @Autowired
    TrainingSessionService trainingSessionService;


    @PostMapping
    public ResponseEntity<TrainingSessionDTO> createTrainingSession(@RequestBody TrainingSessionDTO trainingSessionDTO){
        TrainingSessionDTO savedSession = trainingSessionService.createTrainingSession(trainingSessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSession);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDTO> getReport(@PathVariable Long id) {
        TrainingSessionDTO report = trainingSessionService.getReportSession(id);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/history")
    public ResponseEntity<Page<TrainingSessionDTO>> getHistory(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(trainingSessionService.getHistory(pageable));
    }
}
