package com.example.gymTracker.repository;

import com.example.gymTracker.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {

    long countByTrainingSessionSessionIdAndExerciseExerciseId(Long sessionId, Long exerciseId);

    @Query("SELECT es FROM ExerciseSet es " +
           "JOIN FETCH es.exercise " +
           "JOIN FETCH es.trainingSession " +
           "WHERE es.trainingSession.user.userId = :userId")
    List<ExerciseSet> findByUserWithDetails(@Param("userId") Long userId);

    @Query("SELECT es FROM ExerciseSet es " +
           "JOIN FETCH es.exercise e " +
           "JOIN FETCH e.trainingGroup " +
           "WHERE es.trainingSession.user.userId = :userId " +
           "AND es.trainingSession.date > :startDate")
    List<ExerciseSet> findByUserAndDateAfterWithDetails(@Param("userId") Long userId, @Param("startDate") LocalDate startDate);
}
