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
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "body_weight")
    private BigDecimal bodyWeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;


}
