package com.example.gymTracker.repository;

import com.example.gymTracker.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet,Long> {


    long countByTrainingSessionSessionIdAndExerciseExerciseId(Long sessionId, Long exerciseId);
}
