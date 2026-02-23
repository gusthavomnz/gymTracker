package com.example.gymTracker.dto;

import lombok.Data;
import com.example.gymTracker.model.Enum.GenderEnum;

import java.math.BigDecimal;


@Data
public class UserRegisterDTO {


    private long id;

    private String email;

    private String password;

    private BigDecimal bodyWeight;

    private GenderEnum gender;
}