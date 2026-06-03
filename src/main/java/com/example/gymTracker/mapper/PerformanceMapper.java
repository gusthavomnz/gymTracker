package com.example.gymTracker.mapper;

import com.example.gymTracker.dto.response.PersonalRecordDTO;
import com.example.gymTracker.dto.response.VolumeLoadResponseDTO;
import com.example.gymTracker.model.ExerciseSet;
import com.example.gymTracker.model.TrainingSession;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class PerformanceMapper {

    public VolumeLoadResponseDTO toVolumeLoadDTO(TrainingSession session, BigDecimal totalVolume, Map<String, BigDecimal> volumeByExercise) {
        return new VolumeLoadResponseDTO(
                session.getSessionId(),
                session.getName(),
                totalVolume,
                volumeByExercise
        );
    }

    public PersonalRecordDTO toPersonalRecordDTO(ExerciseSet set, BigDecimal estimated1RM) {
        return new PersonalRecordDTO(
                set.getExercise().getName(),
                set.getWeight(),
                set.getRepetitions(),
                set.getTrainingSession().getDate(),
                estimated1RM
        );
    }
}
