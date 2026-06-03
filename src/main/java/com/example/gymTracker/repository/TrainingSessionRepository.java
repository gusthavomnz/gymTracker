package com.example.gymTracker.repository;

import com.example.gymTracker.model.TrainingSession;
import com.example.gymTracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    @Query(value = "SELECT ts FROM TrainingSession ts " +
                   "JOIN FETCH ts.user " +
                   "JOIN FETCH ts.trainingGroup " +
                   "WHERE ts.user = :user " +
                   "ORDER BY ts.date DESC",
           countQuery = "SELECT COUNT(ts) FROM TrainingSession ts WHERE ts.user = :user")
    Page<TrainingSession> findByUserWithDetailsOrderByDateDesc(@Param("user") User user, Pageable pageable);

    @Query("SELECT ts FROM TrainingSession ts " +
           "JOIN FETCH ts.user " +
           "JOIN FETCH ts.trainingGroup " +
           "LEFT JOIN FETCH ts.exerciseSets es " +
           "LEFT JOIN FETCH es.exercise " +
           "WHERE ts.sessionId = :sessionId")
    Optional<TrainingSession> findFullSession(@Param("sessionId") Long sessionId);
}
