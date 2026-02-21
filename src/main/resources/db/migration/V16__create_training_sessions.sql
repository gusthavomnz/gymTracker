CREATE TABLE training_sessions (
    session_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    tg_id BIGINT NOT NULL,
    date DATE NOT NULL,
    notes VARCHAR(255),
    PRIMARY KEY (session_id),
    CONSTRAINT fk_session_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_session_group FOREIGN KEY (tg_id) REFERENCES training_groups (tg_id)
) ENGINE=InnoDB;