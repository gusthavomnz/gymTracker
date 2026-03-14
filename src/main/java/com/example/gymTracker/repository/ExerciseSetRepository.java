package com.example.gymTracker.repository;

import com.example.gymTracker.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet,Long> {


    long countByTrainingSessionSessionIdAndExerciseExerciseId(Long sessionId, Long exerciseId);

    List<ExerciseSet> findByTrainingSessionUserUserIdAndExerciseExerciseId(Long userId, Long exerciseId);

    List<ExerciseSet> findByTrainingSessionUserUserId(Long userId);

    List<ExerciseSet> findByTrainingSessionUserUserIdAndTrainingSessionDateAfter(Long userId, java.time.LocalDate date);
}
