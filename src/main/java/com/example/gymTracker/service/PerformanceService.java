package com.example.gymTracker.service;

import com.example.gymTracker.config.AuthService;
import com.example.gymTracker.dto.response.PersonalRecordDTO;
import com.example.gymTracker.dto.response.VolumeLoadResponseDTO;
import com.example.gymTracker.mapper.PerformanceMapper;
import com.example.gymTracker.model.ExerciseSet;
import com.example.gymTracker.model.TrainingSession;
import com.example.gymTracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    @Autowired
    private TrainingSessionService trainingSessionService;

    @Autowired
    private ExerciseSetService exerciseSetService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PerformanceMapper performanceMapper;

    public VolumeLoadResponseDTO calculateVolumeLoad(Long sessionId) {
        TrainingSession session = trainingSessionService.findFullSession(sessionId);

        BigDecimal totalVolume = BigDecimal.ZERO;
        Map<String, BigDecimal> volumeByExercise = new HashMap<>();

        for (ExerciseSet set : session.getExerciseSets()) {
            BigDecimal setVolume = set.getWeight().multiply(new BigDecimal(set.getRepetitions()));
            totalVolume = totalVolume.add(setVolume);
            volumeByExercise.merge(set.getExercise().getName(), setVolume, BigDecimal::add);
        }

        return performanceMapper.toVolumeLoadDTO(session, totalVolume, volumeByExercise);
    }

    public BigDecimal calculateEstimated1RM(BigDecimal weight, Integer reps) {
        if (reps == 1) return weight;
        // Brzycki formula: Weight / (1.0278 - 0.0278 * Reps)
        BigDecimal factor = BigDecimal.valueOf(1.0278).subtract(BigDecimal.valueOf(0.0278).multiply(BigDecimal.valueOf(reps)));
        return weight.divide(factor, 2, RoundingMode.HALF_UP);
    }

    public List<PersonalRecordDTO> getPersonalRecords() {
        User user = authService.getAuthenticatedUser();
        List<ExerciseSet> allSets = exerciseSetService.findByUserWithDetails(user.getUserId());

        Map<String, ExerciseSet> prsByExercise = new HashMap<>();
        for (ExerciseSet set : allSets) {
            String exerciseName = set.getExercise().getName();
            if (!prsByExercise.containsKey(exerciseName) || set.getWeight().compareTo(prsByExercise.get(exerciseName).getWeight()) > 0) {
                prsByExercise.put(exerciseName, set);
            }
        }

        return prsByExercise.values().stream()
                .map(set -> performanceMapper.toPersonalRecordDTO(set, calculateEstimated1RM(set.getWeight(), set.getRepetitions())))
                .collect(Collectors.toList());
    }

    public Map<String, Long> getVolumeDistribution(int days) {
        User user = authService.getAuthenticatedUser();
        LocalDate startDate = LocalDate.now().minusDays(days);
        List<ExerciseSet> sets = exerciseSetService.findByUserAndDateAfterWithDetails(user.getUserId(), startDate);

        return sets.stream()
                .collect(Collectors.groupingBy(
                        set -> set.getExercise().getTrainingGroup().getName(),
                        Collectors.counting()
                ));
    }
}
