package com.example.gymTracker.repository;

import com.example.gymTracker.model.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Long> {
}
