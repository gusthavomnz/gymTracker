package model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import model.Enum.GenderEnum;

import java.math.BigDecimal;
@Data
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long user_id;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "bodyWeight")
    private BigDecimal bodyWeight;

    @Column(name = "genderEnum")
    private GenderEnum genderEnum;


}
