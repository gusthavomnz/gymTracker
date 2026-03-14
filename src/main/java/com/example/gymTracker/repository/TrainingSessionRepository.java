package com.example.gymTracker.repository;

import com.example.gymTracker.model.TrainingSession;
import com.example.gymTracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    Page<TrainingSession> findByUserOrderByDateDesc(User user, Pageable pageable);
}
