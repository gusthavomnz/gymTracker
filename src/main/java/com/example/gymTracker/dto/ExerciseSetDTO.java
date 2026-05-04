package com.example.gymTracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ExerciseSetDTO {

    @NotNull(message = "O ID da sessão de treino é obrigatório")
    private Long ts_id;

    @NotNull(message = "O exercício é obrigatório")
    private Long exerciseId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer setNumber;

    @NotNull(message = "O número de repetições é obrigatório")
    @Min(value = 1, message = "O número de repetições deve ser ao menos 1")
    private Integer reps;

    @NotNull(message = "O peso é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O peso não pode ser negativo")
    private BigDecimal weight;

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
