package com.example.gymTracker.controller;

import com.example.gymTracker.dto.request.TrainingSessionRequestDTO;
import com.example.gymTracker.dto.response.TrainingSessionResponseDTO;
import com.example.gymTracker.service.TrainingSessionService;
import jakarta.validation.Valid;
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
    private TrainingSessionService trainingSessionService;

    @PostMapping
    public ResponseEntity<TrainingSessionResponseDTO> createTrainingSession(@RequestBody @Valid TrainingSessionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingSessionService.createTrainingSession(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionResponseDTO> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(trainingSessionService.getReportSession(id));
    }

    @GetMapping("/history")
    public ResponseEntity<Page<TrainingSessionResponseDTO>> getHistory(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(trainingSessionService.getHistory(pageable));
    }
}
