CREATE TABLE exercise_sets (
    set_id BIGINT NOT NULL AUTO_INCREMENT,
    session_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    set_number INT,
    repetitions INT,
    weight DECIMAL(10,2),
    PRIMARY KEY (set_id),
    CONSTRAINT fk_set_session FOREIGN KEY (session_id) REFERENCES training_sessions (session_id),
    CONSTRAINT fk_set_exercise FOREIGN KEY (exercise_id) REFERENCES exercises (exercise_id)
);