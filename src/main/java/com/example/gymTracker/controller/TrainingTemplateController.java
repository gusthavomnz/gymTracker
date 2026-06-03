package com.example.gymTracker.controller;

import com.example.gymTracker.dto.request.TrainingTemplateRequestDTO;
import com.example.gymTracker.dto.response.TrainingTemplateResponseDTO;
import com.example.gymTracker.service.TrainingTemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/templates")
public class TrainingTemplateController {

    @Autowired
    private TrainingTemplateService trainingTemplateService;

    @PostMapping
    public ResponseEntity<TrainingTemplateResponseDTO> create(@RequestBody @Valid TrainingTemplateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingTemplateService.createTemplate(dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<TrainingTemplateResponseDTO>> listMy() {
        return ResponseEntity.ok(trainingTemplateService.listMyTemplates());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainingTemplateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }
}
