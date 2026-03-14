package com.example.gymTracker.controller;

import com.example.gymTracker.dto.response.PersonalRecordDTO;
import com.example.gymTracker.dto.response.VolumeLoadResponseDTO;
import com.example.gymTracker.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/volume-load/{sessionId}")
    public ResponseEntity<VolumeLoadResponseDTO> getVolumeLoad(@PathVariable Long sessionId) {
        return ResponseEntity.ok(performanceService.calculateVolumeLoad(sessionId));
    }

    @GetMapping("/prs")
    public ResponseEntity<List<PersonalRecordDTO>> getPersonalRecords() {
        return ResponseEntity.ok(performanceService.getPersonalRecords());
    }

    @GetMapping("/volume-distribution")
    public ResponseEntity<Map<String, Long>> getVolumeDistribution(@RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(performanceService.getVolumeDistribution(days));
    }

    @GetMapping("/1rm-calc")
    public ResponseEntity<BigDecimal> calculate1RM(@RequestParam BigDecimal weight, @RequestParam Integer reps) {
        return ResponseEntity.ok(performanceService.calculateEstimated1RM(weight, reps));
    }
}
