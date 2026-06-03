package com.example.gymTracker.repository;

import com.example.gymTracker.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT e FROM Exercise e JOIN FETCH e.trainingGroup")
    List<Exercise> findAllWithTrainingGroup();

    @Query("SELECT e FROM Exercise e JOIN FETCH e.trainingGroup WHERE e.trainingGroup.tgId = :tgId")
    List<Exercise> findByTrainingGroupWithDetails(@Param("tgId") Long tgId);

    List<Exercise> findByNameContainingIgnoreCase(String name);

    List<Exercise> findByTrainingGroup_NameContainingIgnoreCase(String muscleGroup);
}
