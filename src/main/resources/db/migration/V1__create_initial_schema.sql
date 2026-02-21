CREATE TABLE training_groups (
    tg_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (tg_id)
) ENGINE=InnoDB;

CREATE TABLE exercises (
    exercise_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    training_group_id BIGINT,
    PRIMARY KEY (exercise_id),
    CONSTRAINT fk_exercise_group
        FOREIGN KEY (training_group_id)
        REFERENCES training_groups (tg_id)
) ENGINE=InnoDB;