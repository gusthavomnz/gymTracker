package com.example.gymTracker.repository;

import com.example.gymTracker.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{


    List<Exercise> findByTrainingGroup_TgId(Long tgId);
}
