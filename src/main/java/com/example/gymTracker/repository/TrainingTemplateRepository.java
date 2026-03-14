package com.example.gymTracker.repository;

import com.example.gymTracker.model.TrainingTemplate;
import com.example.gymTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingTemplateRepository extends JpaRepository<TrainingTemplate, Long> {
    List<TrainingTemplate> findByUser(User user);
}
