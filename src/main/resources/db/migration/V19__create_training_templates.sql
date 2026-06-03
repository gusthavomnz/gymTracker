CREATE TABLE training_templates (
    template_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    user_id BIGINT,
    PRIMARY KEY (template_id),
    CONSTRAINT fk_template_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE template_exercises (
    template_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    PRIMARY KEY (template_id, exercise_id),
    CONSTRAINT fk_te_template FOREIGN KEY (template_id) REFERENCES training_templates(template_id),
    CONSTRAINT fk_te_exercise FOREIGN KEY (exercise_id) REFERENCES exercises(exercise_id)
);
