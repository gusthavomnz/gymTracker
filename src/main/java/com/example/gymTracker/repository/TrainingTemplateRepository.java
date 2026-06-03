package com.example.gymTracker.repository;

import com.example.gymTracker.model.TrainingTemplate;
import com.example.gymTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingTemplateRepository extends JpaRepository<TrainingTemplate, Long> {

    @Query("SELECT DISTINCT tt FROM TrainingTemplate tt LEFT JOIN FETCH tt.exercises WHERE tt.user = :user")
    List<TrainingTemplate> findByUserWithExercises(@Param("user") User user);
}
